package com.sellman.andrew.vann.core.training;

import java.util.List;

import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputListener;
import com.sellman.andrew.vann.core.event.NetworkLayerInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerInputListener;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputListener;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.training.evaluator.RollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer implements NetworkLayerInputListener, NetworkLayerBiasedWeightedInputListener, NetworkLayerOutputListener {
	private final FeedForwardNetwork network;
	private final FeedForwardNetworkTrainerConfig config;
	private boolean training;
	private TrainingProgress trainingProgress;

	public FeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		this.network = network;
		this.config = config;
		registerForEvents();
	}

	public final void train(final List<TrainingExample> allTrainingData) {
		int nthEpochSavePoint = getMinimumRollbackEpochCount();
		
		trainingProgress = new TrainingProgress(network.getContext(), config.getEventManager(), network.getLayerCount(), nthEpochSavePoint, config.getSavePointDepth());
		trainingProgress.setLearningRate(config.getLearningRateEvaluator().getLearningRate());
		trainingProgress.setMomentum(config.getMomentumEvaluator().getMomentum());
		
		if (trainingProgress.isEpochSavePoint()) {
			doEpochSavePoint();
		}
		
		do {
			training = true;
			SplitTrainingExamples splitData = config.getTrainingSplitter().split(allTrainingData, trainingProgress);
			trainEpoch(splitData.getForTraining());
			training = false;

			validateEpoch(getValidationBatch(splitData.getForValidation()));
			
			boolean rollbackEpoch = shouldRollbackEpoch();
			trainingProgress.setRollingBackEpoch(rollbackEpoch); 
			
			trainingProgress.setLearningRate(getAdjustedLearningRate());
			trainingProgress.setMomentum(getAdjustedMomentum());
			
			if (rollbackEpoch) {
				doEpochRollBack();
			} else if (trainingProgress.isEpochSavePoint()) {
				doEpochSavePoint();
			}
		
		} while (!isFinishedTraining());
	}
	
	private int getMinimumRollbackEpochCount() {
		int epochsToRollBack = Integer.MAX_VALUE;
		for (RollbackEvaluator rollBackEvaluator : config.getRollbackEvaluators()) {
			if (rollBackEvaluator.getEpochRollbackCount() < epochsToRollBack) {
				epochsToRollBack = rollBackEvaluator.getEpochRollbackCount();
			}
		}

		return epochsToRollBack;
	}

	
	private boolean shouldRollbackEpoch() {
		for (RollbackEvaluator rollbackEvaluator : config.getRollbackEvaluators()) {
			if (rollbackEvaluator.shouldRollback(trainingProgress)) {
				return true;
			}
		}
		
		return false;
	}

	private void doEpochRollBack() {
		for (int l = 0; l < network.getLayerCount(); l++) {
			network.setWeights(l, trainingProgress.getWeightsSavePoint(l));
			network.setBias(l, trainingProgress.getBiasSavePoint(l));
		}
	}

	private void doEpochSavePoint() {
		for (int l = 0; l < network.getLayerCount(); l++) {
			trainingProgress.setWeightsSavePoint(l, new Matrix(network.getWeights(l)));
			trainingProgress.setBiasSavePoint(l, new RowVector(network.getBias(l)));
		}
	}

	private void validateEpoch(TrainingBatch validationBatch) {
//		trainingProgress.resetValidationError();
		InspectableMatrix actualOutput = network.evaluate(validationBatch.getInput());
		Matrix outputDifference = subtract(actualOutput, validationBatch.getExpectedOutput());
		Matrix absoluteOutputDifference = absolute(outputDifference);
		double validationError = sum(absoluteOutputDifference);
		trainingProgress.setValidationError(validationError);
	}

	private void trainEpoch(List<TrainingExample> trainingData) {
		trainingProgress.resetEpochError();
//		trainingProgress.resetValidationError();
		trainingProgress.resetBatchIndex();
		
		trainingProgress.setBatchCount(getBatchCount(trainingData.size()));

		while (trainingProgress.getBatchIndex() < trainingProgress.getBatchCount()) {
			trainingProgress.resetBatchError();
			TrainingBatch batch = getTrainingBatch(trainingData);
			trainBatch(batch);
			trainingProgress.incrementBatchIndex();
		}

		trainingProgress.setEpochError(trainingProgress.getBatchError());
		trainingProgress.incrementEpochIndex();
	}

	protected TrainingBatch getValidationBatch(List<TrainingExample> validationData) {
		return config.getValidationBatchProvider().getTrainingBatch(trainingProgress, validationData);
	}

	protected TrainingBatch getTrainingBatch(List<TrainingExample> trainingData) {
		return config.getTrainingBatchProvider().getTrainingBatch(trainingProgress, trainingData);
	}

	protected void trainBatch(TrainingBatch batch) {
		InspectableMatrix actualOutput = feedForward(batch.getInput());
		InspectableMatrix outputDifference = subtract(actualOutput, batch.getExpectedOutput());

		double batchError = getErrorCalculator().getError(outputDifference);
		trainingProgress.accumulateBatchError(batchError);

		InspectableMatrix input = getNetworkInput(getLayerCount() - 1);

		InspectableMatrix primeOutput = getBiasedWeightedPrimeOutput(getLayerCount() - 1);
		InspectableMatrix outputDelta = hadamard(outputDifference, primeOutput);
		setNetworkOutputDelta(getLayerCount() - 1, outputDelta);

		for (int l = getLayerCount() - 2; l >= 0; l--) {
			Matrix nextLayerWeights = getNetworkWeights(l + 1);
			Matrix transposedNextLayerWeights = transpose(nextLayerWeights);
			InspectableMatrix nextLayerOutputDelta = getNetworkOutputDelta(l + 1);
			Matrix weightsDelta = multiply(nextLayerOutputDelta, transposedNextLayerWeights);
			primeOutput = getBiasedWeightedPrimeOutput(l);
			outputDelta = hadamard(weightsDelta, primeOutput);
			setNetworkOutputDelta(l, outputDelta);
		}

		for (int l = 0; l < getLayerCount(); l++) {
			outputDelta = getNetworkOutputDelta(l);
			Matrix transposedOutputDelta = transpose(outputDelta);
			input = getNetworkInput(l);
			Matrix weightError = multiply(transposedOutputDelta, input);

			Matrix weightDelta = scale(weightError, x -> x * getLearningRate());
			InspectableMatrix lastWeightDelta = getLastWeightDelta(l);
			if (lastWeightDelta != null) {
				Matrix scaledLastWeightDelta = scale(lastWeightDelta, x -> x * getMomentum());
				weightDelta = add(weightDelta, scaledLastWeightDelta);
			}
			setLastWeightDelta(l, weightDelta);
			
			Matrix transposedWeightDelta = transpose(weightDelta);
			Matrix currentWeights = getNetworkWeights(l);
			Matrix newWeights = subtract(currentWeights, transposedWeightDelta);
			setNetworkWeights(l, newWeights);

			Matrix scaledOutputDelta = scale(outputDelta, x -> x * getLearningRate());
			RowVector currentBias = getNetworkBias(l);
			RowVector newBias = subtract(currentBias, scaledOutputDelta);
			RowVector biasDelta = subtract(currentBias, newBias);
			RowVector lastBiasDelta = getLastBiasDelta(l);
			if (lastBiasDelta != null) {
				RowVector scaledLastBiasDelta = scale(lastBiasDelta, x -> x * getMomentum());
				biasDelta = add(biasDelta, scaledLastBiasDelta);
				newBias = add(newBias, scaledLastBiasDelta);
			}
			setLastBiasDelta(l, biasDelta);
			setNetworkBias(l, newBias);
		}
	}

	private double getMomentum() {
		return trainingProgress.getMomentum();
	}

	private ErrorCalculator getErrorCalculator() {
		return config.getErrorCalculator();
	}

	private boolean isFinishedTraining() {
		List<TrainingEvaluator> trainingEvaluations = config.getTrainingEvaluators();
		for (TrainingEvaluator trainingEvaluator : trainingEvaluations) {
			if (trainingEvaluator.isFinishedTraining(trainingProgress)) {
				return true;
			}
		}

		return false;
	}

	protected int getBatchCount(int totalTraingItems) {
		return config.getTrainingBatchProvider().getBatchCount(totalTraingItems);
	}

	protected final InspectableMatrix feedForward(InspectableMatrix input) {
		return network.evaluate(input);
	}

	protected final InspectableMatrix getNetworkInput(int layerIndex) {
		return trainingProgress.getInput(layerIndex);
	}

	protected final InspectableMatrix getBiasedWeightedPrimeOutput(int layerIndex) {
		return trainingProgress.getBiasedWeightedPrimeOutput(layerIndex);
	}

	protected final void setNetworkOutputDelta(int layerIndex, InspectableMatrix outputDelta) {
		trainingProgress.setOutputDelta(layerIndex, outputDelta);
	}

	protected final InspectableMatrix getNetworkOutputDelta(int layerIndex) {
		return trainingProgress.getOutputDelta(layerIndex);
	}

	protected final void setNetworkWeights(int layerIndex, Matrix weights) {
		network.setWeights(layerIndex, weights);
	}

	protected final Matrix getNetworkWeights(int layerIndex) {
		return network.getWeights(layerIndex);
	}

	protected final RowVector getNetworkBias(int layerIndex) {
		return network.getBias(layerIndex);
	}

	protected final void setNetworkBias(int layerIndex, RowVector bias) {
		network.setBias(layerIndex, bias);
	}

	protected final Matrix subtract(InspectableMatrix left, InspectableMatrix right) {
		return getMathOps().subtract(left, right);
	}

	protected final Matrix scale(InspectableMatrix m, Function f) {
		return getMathOps().scale(m, f);
	}

	protected final RowVector scale(RowVector v, Function f) {
		return getMathOps().scale(v, f);
	}

	protected final Matrix multiply(ColumnVector left, Matrix right) {
		return getMathOps().multiply(left, right);
	}

	protected final Matrix multiply(InspectableMatrix left, InspectableMatrix right) {
		return getMathOps().multiply(left, right);
	}

	protected final ColumnVector multiply(Matrix left, ColumnVector right) {
		return getMathOps().multiply(left, right);
	}

	protected final Matrix transpose(InspectableMatrix m) {
		return getMathOps().transpose(m);
	}

	protected final Matrix hadamard(InspectableMatrix a, InspectableMatrix b) {
		return getMathOps().hadamard(a, b);
	}

	protected final ColumnVector hadamard(ColumnVector a, ColumnVector b) {
		return getMathOps().hadamard(a, b);
	}

	protected final ColumnVector scale(ColumnVector v, Function f) {
		return getMathOps().scale(v, f);
	}

	private MathOperations getMathOps() {
		return config.getMathOperations();
	}

	protected final double getLearningRate() {
		return trainingProgress.getLearningRate();
	}

	protected final double getAdjustedLearningRate() {
		return config.getLearningRateEvaluator().getAdjustedLearningRate(trainingProgress);
	}

	protected final double getAdjustedMomentum() {
		return config.getMomentumEvaluator().getAdjustedMomentum(trainingProgress);
	}

	protected final int getLayerCount() {
		return network.getLayerCount();
	}

	protected final ColumnVector absolute(ColumnVector v) {
		return getMathOps().absolute(v);
	}

	protected final Matrix absolute(Matrix m) {
		return getMathOps().absolute(m);
	}

	protected final RowVector subtract(RowVector left, InspectableMatrix right) {
		InspectableMatrix leftMatrix = config.getMatrixFactory().createFor(left, right.getRowCount());
		Matrix result = getMathOps().subtract(leftMatrix, right);
		return config.getRowVectorFactory().createFrom(result, 0);
	}

	private final RowVector subtract(RowVector left, RowVector right) {
		return getMathOps().subtract(left, right);
	}

	private Matrix add(InspectableMatrix a, InspectableMatrix b) {
		return getMathOps().add(a, b);
	}
	
	private RowVector add(RowVector a, RowVector b) {
		return getMathOps().add(a, b);
	}

	protected final double sum(InspectableMatrix m) {
		return getMathOps().sum(m);
	}

	protected final double sum(ColumnVector v) {
		return getMathOps().sum(v);
	}

	protected final double sum(Matrix m) {
		return getMathOps().sum(m);
	}

	@Override
	public void onEvent(NetworkLayerBiasedWeightedInputEvent event) {
		if (!isTraining()) {
			return;
		}
		
		InspectableMatrix biasedWeightedInput = event.getBiasedWeightedInput();
		int layerIndex = event.getNetworkLayerIndex();
		trainingProgress.setBiasedWeightedInput(biasedWeightedInput, layerIndex);
		
		InspectableMatrix biasedPrimeOutput = scale(biasedWeightedInput, network.getActivationPrimeFunction(layerIndex));
		trainingProgress.setBiasedWeightedPrimeOutput(biasedPrimeOutput, layerIndex);
	}


	private void setLastWeightDelta(int layerIndex, InspectableMatrix weightDelta) {
		trainingProgress.setLastWeightDelta(layerIndex, weightDelta);
	}

	private InspectableMatrix getLastWeightDelta(int layerIndex) {
		return trainingProgress.getLastWeightDelta(layerIndex);
	}

	private void setLastBiasDelta(int layerIndex, RowVector biasDelta) {
		trainingProgress.setLastBiasDelta(layerIndex, biasDelta);
	}

	private RowVector getLastBiasDelta(int layerIndex) {
		return trainingProgress.getLastBiasDelta(layerIndex);
	}

	@Override
	public void onEvent(NetworkLayerOutputEvent event) {
		if (!isTraining()) {
			return;
		}
		
		trainingProgress.setOutput(event.getOutput(), event.getNetworkLayerIndex());
	}

	@Override
	public void onEvent(NetworkLayerInputEvent event) {
		if (!isTraining()) {
			return;
		}
		
		trainingProgress.setInput(event.getInput(), event.getNetworkLayerIndex());
	}

	private void registerForEvents() {
		config.getEventManager().registerForImmediateNotification(this, NetworkLayerInputEvent.class);
		config.getEventManager().registerForImmediateNotification(this, NetworkLayerBiasedWeightedInputEvent.class);
		config.getEventManager().registerForImmediateNotification(this, NetworkLayerInputEvent.class);
	}

	private boolean isTraining() {
		return training;
	}
	
	
}
