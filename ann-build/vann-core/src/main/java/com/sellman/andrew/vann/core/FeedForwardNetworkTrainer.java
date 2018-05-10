package com.sellman.andrew.vann.core;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.training.ErrorCalculator;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.vann.core.training.TrainingProgress;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateRecommendation;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer {
	private final FeedForwardNetwork network;
	private final FeedForwardNetworkTrainerConfig config;

	public FeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		this.network = network;
		this.config = config;
	}

	public final void train(final List<TrainingItem> trainingItems) {
		int splitIndex = (int) (trainingItems.size() * config.getPercentTrainingDataForValidation());
		List<TrainingItem> trainingData = trainingItems.stream().limit(trainingItems.size() - splitIndex).collect(Collectors.toList());
		List<TrainingItem> validationData = trainingItems.stream().skip(trainingItems.size() - splitIndex).collect(Collectors.toList());

		int batchCount = getBatchCount(trainingData.size(), getBatchSize());
		TrainingProgress progress = new TrainingProgress(network.getContext(), config.getEventManager(), batchCount);

		TrainingBatch validationBatch = getTrainingBatch(validationData);

		do {
			network.setTraining(true);
			trainEpoch(trainingData, progress);
			network.setTraining(false);

			validateEpoch(validationBatch, progress);

		} while (!isFinishedTraining(progress));
	}

	private void validateEpoch(TrainingBatch validationBatch, TrainingProgress progress) {
		progress.resetValidationError();
		InspectableMatrix actualOutput = network.evaluate(validationBatch.getInput());
		Matrix outputDifference = subtract(actualOutput, validationBatch.getExpectedOutput());
		Matrix absoluteOutputDifference = absolute(outputDifference);
		double outputError = sum(absoluteOutputDifference);
		progress.setValidationError(outputError);
	}

	private void trainEpoch(List<TrainingItem> trainingData, TrainingProgress progress) {
		Collections.shuffle(trainingData);

		progress.resetEpochError();
		progress.resetValidationError();
		progress.resetBatchIndex();

		while (progress.getBatchIndex() < progress.getBatchCount()) {
			progress.resetBatchError();
			TrainingBatch batch = getTrainingBatch(trainingData, progress);
			trainBatch(batch, progress);
			progress.incrementBatchIndex();
		}

		progress.setEpochError(progress.getBatchError());
		progress.incrementEpochIndex();
	}

	protected TrainingBatch getTrainingBatch(List<TrainingItem> trainingData, TrainingProgress progress) {
		int fromIndex = progress.getBatchIndex() * getBatchSize();
		int toIndex = fromIndex + getBatchSize();
		if (toIndex > trainingData.size()) {
			toIndex = trainingData.size();
		}

		List<TrainingItem> batchTrainingData = trainingData.subList(fromIndex, toIndex);
		return getTrainingBatch(batchTrainingData);
	}

	protected void trainBatch(TrainingBatch batch, TrainingProgress progress) {
		InspectableMatrix actualOutput = feedForward(batch.getInput());
		InspectableMatrix outputDifference = subtract(actualOutput, batch.getExpectedOutput());

		double batchError = getErrorCalculator().getError(outputDifference);
		progress.accumulateBatchError(batchError);

		InspectableMatrix input = getNetworkInput(getLayerCount() - 1);

		InspectableMatrix primeOutput = getNetworkBiasedPrimeOutput(getLayerCount() - 1);
		Matrix outputDelta = hadamard(outputDifference, primeOutput);
		setNetworkOutputDelta(getLayerCount() - 1, outputDelta);

		for (int l = getLayerCount() - 2; l >= 0; l--) {
			Matrix nextLayerWeights = getNetworkWeights(l + 1);
			Matrix transposedNextLayerWeights = transpose(nextLayerWeights);
			Matrix nextLayerOutputDelta = getNetworkOutputDelta(l + 1);
			Matrix weightsDelta = multiply(nextLayerOutputDelta, transposedNextLayerWeights);
			primeOutput = getNetworkBiasedPrimeOutput(l);
			outputDelta = hadamard(weightsDelta, primeOutput);
			setNetworkOutputDelta(l, outputDelta);
		}

		for (int l = 0; l < getLayerCount(); l++) {
			outputDelta = getNetworkOutputDelta(l);
			Matrix transposedOutputDelta = transpose(outputDelta);
			input = getNetworkInput(l);
			Matrix weightError = multiply(transposedOutputDelta, input);

			Matrix scaledWeightError = scale(weightError, x -> x * getLearningRate(progress));
			Matrix transposedScaledWeightError = transpose(scaledWeightError);

			Matrix currentWeights = getNetworkWeights(l);
			Matrix newWeights = subtract(currentWeights, transposedScaledWeightError);
			setNetworkWeights(l, newWeights);

			Matrix scaledOutputDelta = scale(outputDelta, x -> x * getLearningRate(progress));
			RowVector currentBias = getNetworkBias(l);
			RowVector newBias = subtract(currentBias, scaledOutputDelta);
			setNetworkBias(l, newBias);
		}
	}

	private ErrorCalculator getErrorCalculator() {
		return config.getErrorCalculator();
	}

	private TrainingBatch getTrainingBatch(List<TrainingItem> trainingData) {
		return config.getTrainingBatchFactory().createFor(trainingData);
	}

	private boolean isFinishedTraining(TrainingProgress progress) {
		List<TrainingEvaluator> trainingEvaluations = config.getTrainingEvaluators();
		for (TrainingEvaluator trainingEvaluator : trainingEvaluations) {
			if (trainingEvaluator.isFinishedTraining(progress)) {
				return true;
			}
		}

		return false;
	}

	protected int getBatchCount(int traingDataSize, int batchSize) {
		int batchCount = traingDataSize / batchSize;
		if (traingDataSize % batchSize > 0) {
			batchCount++;
		}

		return batchCount;
	}

	protected final InspectableMatrix feedForward(InspectableMatrix input) {
		return network.evaluate(input);
	}

	protected final InspectableMatrix getNetworkInput(int layerIndex) {
		return network.getInput(layerIndex);
	}

	protected final Matrix getNetworkBiasedPrimeOutput(int layerIndex) {
		return network.getBiasedPrimeOutput(layerIndex);
	}

	protected final void setNetworkOutputDelta(int layerIndex, Matrix outputDelta) {
		network.setOutputDelta(layerIndex, outputDelta);
	}

	protected final Matrix getNetworkOutputDelta(int layerIndex) {
		return network.getOutputDelta(layerIndex);
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

	protected final int getBatchSize() {
		return config.getBatchSize();
	}

	private MathOperations getMathOps() {
		return config.getMathOperations();
	}

	protected final double getLearningRate(TrainingProgress progress) {
		return getLearningRateRecommendation(progress).getLearningRate();
	}

	protected final LearningRateRecommendation getLearningRateRecommendation(TrainingProgress progress) {
		return config.getLearningRateEvaluator().getRecommendation(progress);
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

	protected final ColumnVector subtract(ColumnVector left, InspectableMatrix right) {
		return getMathOps().subtract(left, right);
	}

	protected final RowVector subtract(RowVector left, InspectableMatrix right) {
		return getMathOps().subtract(left, right);
	}

	protected final ColumnVector subtract(ColumnVector left, ColumnVector right) {
		return getMathOps().subtract(left, right);
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

}
