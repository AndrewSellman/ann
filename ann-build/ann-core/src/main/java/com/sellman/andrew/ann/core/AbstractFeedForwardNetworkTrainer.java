package com.sellman.andrew.ann.core;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.TrainingProgress;
import com.sellman.andrew.ann.core.training.evaluator.LearningRateRecommendation;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public abstract class AbstractFeedForwardNetworkTrainer {
	private final FeedForwardNetwork network;
	private final FeedForwardNetworkTrainerConfig config;

	public AbstractFeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		this.network = network;
		this.config = config;
	}

	public final void train(final List<TrainingItem> trainingItems) {
		int splitIndex = (int) (trainingItems.size() * config.getPercentTrainingDataForValidation());
		List<TrainingItem> trainingData = trainingItems.stream().limit(trainingItems.size() - splitIndex).collect(Collectors.toList());
		List<TrainingItem> validationData = trainingItems.stream().skip(trainingItems.size() - splitIndex).collect(Collectors.toList());

		int batchCount = getBatchCount(trainingData.size(), getBatchSize());
		TrainingProgress progress = new TrainingProgress(network.getContext(), config.getEventManager(), batchCount);

		do {
			network.setTraining(true);
			trainEpoch(trainingData, progress);
			network.setTraining(false);

			validateEpoch(validationData, progress);

		} while (!isFinishedTraining(progress));
	}

	private void validateEpoch(List<TrainingItem> validationData, TrainingProgress progress) {
		progress.resetValidationError();
		double outputError = 0;
		for (TrainingItem validationExample : validationData) {
			Vector actualOutput = network.evaluate(validationExample.getInput());
			Vector outputDifference = subtract(actualOutput, validationExample.getExpectedOutput());
			Vector absoluteOutputDifference = absolute(outputDifference);
			outputError += sum(absoluteOutputDifference);
		}
		progress.setValidationError(outputError);
	}

	private void trainEpoch(List<TrainingItem> trainingData, TrainingProgress progress) {
		Collections.shuffle(trainingData);

		progress.resetEpochError();
		progress.resetValidationError();
		progress.resetBatchIndex();

		while (progress.getBatchIndex() < progress.getBatchCount()) {
			progress.resetBatchError();
			List<TrainingItem> batchTrainingItems = getBatchTrainingItems(trainingData, progress);
			trainBatch(batchTrainingItems, progress);
			progress.incrementBatchIndex();
		}

		progress.setEpochError(progress.getBatchError());
		progress.incrementEpochIndex();
	}

	protected List<TrainingItem> getBatchTrainingItems(List<TrainingItem> trainingData, TrainingProgress progress) {
		int fromIndex = progress.getBatchIndex() * getBatchSize();
		int toIndex = fromIndex + getBatchSize();
		if (toIndex > trainingData.size()) {
			toIndex = trainingData.size();
		}
		return trainingData.subList(fromIndex, toIndex);
	}

	protected abstract void trainBatch(List<TrainingItem> batchTrainingItems, TrainingProgress progress);
	
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

	protected final Vector feedForward(Vector input) {
		return network.evaluate(input);
	}
	
	protected final Vector getNetworkInput(int layerIndex) {
		return network.getInput(layerIndex);
	}

	protected final Vector getNetworkBiasedPrimeOutput(int layerIndex) {
		return network.getBiasedPrimeOutput(layerIndex);
	}

	protected final void setNetworkOutputDelta(int layerIndex, Vector outputDelta) {
		network.setOutputDelta(layerIndex, outputDelta);
	}
	
	protected final Vector getNetworkOutputDelta(int layerIndex) {
		return network.getOutputDelta(layerIndex);
	}

	protected final void setNetworkWeights(int layerIndex, Matrix weights) {
		network.setWeights(layerIndex, weights);
	}
	
	protected final Matrix getNetworkWeights(int layerIndex) {
		return network.getWeights(layerIndex);
	}
	
	protected final Vector getNetworkBias(int layerIndex) {
		return network.getBias(layerIndex);
	}

	protected final void setNetworkBias(int layerIndex, Vector bias) {
		network.setBias(layerIndex, bias);
	}

	protected final Matrix subtract(Matrix left, Matrix right) {
		return getMathOps().subtract(left, right);
	}

	protected final Matrix scale(Matrix m, Function f) {
		return getMathOps().scale(m, f);
	}

	protected final Matrix multiply(Vector left, Matrix right) {
		return getMathOps().multiply(left, right);
	}

	protected final Matrix transpose(Vector v) {
		return getMathOps().transpose(v);
	}

	protected final Vector multiply(Matrix left, Vector right) {
		return getMathOps().multiply(left, right);
	}

	protected final Matrix transpose(Matrix m) {
		return getMathOps().transpose(m);
	}

	protected final Vector hadamard(Vector a, Vector b) {
		return getMathOps().hadamard(a, b);
	}

	protected final Vector scale(Vector v, Function f) {
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

	protected final Vector absolute(Vector v) {
		return getMathOps().absolute(v);
	}

	protected final Vector subtract(Vector left, Vector right) {
		return getMathOps().subtract(left, right);
	}

	protected final double sum(Vector v) {
		return getMathOps().sum(v);
	}


}
