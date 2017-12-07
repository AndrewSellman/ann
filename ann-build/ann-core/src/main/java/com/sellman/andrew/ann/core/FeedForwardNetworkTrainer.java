package com.sellman.andrew.ann.core;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.TrainingProgress;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer {
	private final FeedForwardNetwork network;
	private final FeedForwardNetworkTrainerConfig config;

	public FeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		this.network = network;
		this.config = config;
	}

	public void train(List<TrainingItem> trainingData) {
		TrainingProgress progress = new TrainingProgress(network.getContext(), config.getEventManager());
		
		int splitIndex = (int) (trainingData.size() * config.getPercentTrainingDataForValidation());
		List<TrainingItem> validationData = trainingData.stream().skip(trainingData.size() - splitIndex).collect(Collectors.toList());
		trainingData = trainingData.stream().limit(trainingData.size() - splitIndex).collect(Collectors.toList());

		do {
			trainEpoch(trainingData, progress);
			validateEpoch(validationData, progress);
			network.setTraining(true);
			
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
		network.setTraining(true);
		Collections.shuffle(trainingData);

		progress.incrementEpochIndex();
		progress.resetBatchIndex();
		progress.resetEpochError();
		progress.resetBatchError();
		progress.resetValidationError();

		for (int b = 0; b < trainingData.size(); b += getBatchSize()) {
			progress.incrementBatchIndex();
			trainBatch(trainingData.get(b), progress);
		}

		progress.setEpochError(progress.getBatchError());
		network.setTraining(false);
	}

	private void trainBatch(TrainingItem example, TrainingProgress progress) {
		Vector actualOutput = network.evaluate(example.getInput());
		Vector outputDifference = getMatrixOps().subtract(actualOutput, example.getExpectedOutput());

		Vector outputError = getMatrixOps().scale(outputDifference, x -> 1.0 / 2.0 * Math.pow(x, 2));
		double batchError = sum(outputError);
		progress.accumulateBatchError(batchError);

		Vector input = network.getInput(getLayerCount() - 1);

		Vector primeOutput = network.getBiasedPrimeOutput(getLayerCount() - 1);
		Vector outputDelta = getMatrixOps().hadamard(outputDifference, primeOutput);
		network.setOutputDelta(getLayerCount() - 1, outputDelta);

		for (int l = getLayerCount() - 2; l >= 0; l--) {
			Matrix nextLayerWeights = network.getWeights(l + 1);
			Matrix transposedNextLayerWeights = getMatrixOps().transpose(nextLayerWeights);
			Vector nextLayerOutputDelta = network.getOutputDelta(l + 1);
			Vector weightsDelta = getMatrixOps().multiply(transposedNextLayerWeights, nextLayerOutputDelta);
			primeOutput = network.getBiasedPrimeOutput(l);
			outputDelta = getMatrixOps().hadamard(weightsDelta, primeOutput);
			network.setOutputDelta(l, outputDelta);
		}

		for (int l = 0; l < getLayerCount(); l++) {
			outputDelta = network.getOutputDelta(l);
			input = network.getInput(l);
			Matrix transposedInput = getMatrixOps().transpose(input);
			Matrix weightError = getMatrixOps().multiply(outputDelta, transposedInput);

			Matrix scaledWeightError = getMatrixOps().scale(weightError, x -> x * getLearningRate());

			Matrix currentWeights = network.getWeights(l);

			Matrix newWeights = getMatrixOps().subtract(currentWeights, scaledWeightError);
			network.setWeights(l, newWeights);

			Vector scaledOutputDelta = getMatrixOps().scale(outputDelta, x -> x * getLearningRate());
			Vector currentBias = network.getBias(l);
			Vector newBias = getMatrixOps().subtract(currentBias, scaledOutputDelta);
			network.setBias(l, newBias);
		}
	}

	private boolean isFinishedTraining(TrainingProgress progress) {
		AtomicBoolean finishedTraining = new AtomicBoolean();
		List<TrainingEvaluator> trainingEvaluations = config.getTrainingEvaluators();
		for (TrainingEvaluator trainingEvaluator : trainingEvaluations) {
			trainingEvaluator.initFinishedTraining(finishedTraining);
			trainingEvaluator.initTrainingProgress(progress);
		}

		config.getTaskService().runTasks(trainingEvaluations);
		return finishedTraining.get();
	}

	private int getBatchSize() {
		return config.getBatchSize();
	}

	private MatrixOperations getMatrixOps() {
		return config.getMatrixOperations();
	}

	private double getLearningRate() {
		return config.getLearningRate();
	}

	private int getLayerCount() {
		return network.getLayerCount();
	}

	private Matrix absolute(Matrix m) {
		return getMatrixOps().absolute(m);
	}

	private Vector absolute(Vector v) {
		return getMatrixOps().absolute(v);
	}

	private Matrix subtract(Matrix left, Matrix right) {
		return getMatrixOps().subtract(left, right);
	}

	private Vector subtract(Vector left, Vector right) {
		return getMatrixOps().subtract(left, right);
	}

	private double sum(Matrix m) {
		return getMatrixOps().sum(m);
	}

	private double sum(Vector v) {
		return getMatrixOps().sum(v);
	}

}
