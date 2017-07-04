package com.sellman.andrew.ann.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.TrainingProgress;
import com.sellman.andrew.ann.core.training.TrainingStatistics;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer {
	private final FeedForwardNetwork network;
	private final FeedForwardNetworkTrainerConfig config;

	public FeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		this.network = network;
		this.config = config;
	}

	public void train(List<TrainingData> trainingData) {
		TrainingProgress progress = new TrainingProgress();
		TrainingStatistics statistics = new TrainingStatistics();
		progress.setStatistics(statistics);

		network.setTraining(true);
		while (true) {
			statistics.incrementEpoch();
			trainEpoch(trainingData, progress);
			if (isFinishedTraining(progress)) {
				break;
			}
		}

		network.setTraining(false);
	}

	private void trainEpoch(List<TrainingData> trainingData, TrainingProgress progress) {
		if (config.isTrainingDataShuffledEachEpoch()) {
			Collections.shuffle(trainingData);
		}

		progress.getStatistics().resetBatch();
		progress.resetAccumulatedBatchErrors();

		for (int b = 0; b < trainingData.size(); b += getBatchSize()) {
			progress.getStatistics().incrementBatch();
//			System.out.println("epoch: " + progress.getStatistics().getEpoch() + " batch: " + progress.getStatistics().getBatch());
			trainExample(trainingData.get(b), progress);
		}
		
		System.out.println("epoch: " + progress.getStatistics().getEpoch() + " error: " + progress.getAccumulatedBatchErrors());
		if (progress.getStatistics().getEpoch() % 10 == 0) {
			System.out.println();
		}
	}

	private void trainExample(TrainingData example, TrainingProgress progress) {
		Matrix actualOutput = network.evaluate(example.getInput());
		Matrix outputDifference = getMatrixOps().subtract(actualOutput, example.getExpectedOutput());
		
		Matrix outputError = getMatrixOps().scale(outputDifference, x -> 1.0 / 2.0 * Math.pow(x, 2));
		double batchError = sum(outputError);
		progress.addToAccumulatedBatchErrors(batchError);
		
		Matrix input = network.getInput(getLayerCount() - 1);
		
		Matrix primeOutput = network.getBiasedPrimeOutput(getLayerCount() - 1);
		Matrix outputDelta = getMatrixOps().hadamard(outputDifference, primeOutput);
		network.setOutputDelta(getLayerCount() - 1, outputDelta);

		for (int l = getLayerCount() - 2; l >= 0; l--) {
			Matrix nextLayerWeights = network.getWeights(l + 1);
			Matrix transposedNextLayerWeights = getMatrixOps().transpose(nextLayerWeights);
			Matrix nextLayerOutputDelta = network.getOutputDelta(l + 1);
			Matrix weightsDelta = getMatrixOps().multiply(transposedNextLayerWeights, nextLayerOutputDelta);

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
			getMatrixOps().update(newWeights, currentWeights);

			Matrix scaledOutputDelta = getMatrixOps().scale(outputDelta, x -> x * getLearningRate());
			Matrix currentBias = network.getBias(l);
			Matrix newBias = getMatrixOps().subtract(currentBias, scaledOutputDelta);
			getMatrixOps().update(newBias, currentBias);
		}
	}

	private boolean isFinishedTraining(TrainingProgress progress) {
		Iterator<TrainingEvaluator> trainingEvaluations = config.getTrainingEvaluators();
		while (trainingEvaluations.hasNext()) {
			TrainingEvaluator trainingEvaluator = trainingEvaluations.next();
			if (trainingEvaluator.isFinishedTraining(progress)) {
				return true;
			}
		}

		return false;
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
	
	private double sum(Matrix m) {
		return getMatrixOps().sum(m);
	}

	private Matrix absolute(Matrix m) {
		return getMatrixOps().absolute(m);
	}

}
