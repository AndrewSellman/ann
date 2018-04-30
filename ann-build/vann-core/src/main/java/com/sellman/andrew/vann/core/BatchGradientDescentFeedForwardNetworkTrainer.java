package com.sellman.andrew.vann.core;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.vann.core.training.TrainingProgress;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateRecommendation;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class BatchGradientDescentFeedForwardNetworkTrainer extends AbstractFeedForwardNetworkTrainer {
	
	public BatchGradientDescentFeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		super(config, network);
	}

	protected void trainBatch(List<TrainingItem> batchTrainingItems, TrainingProgress progress) {
//		network.resetAccumulationDuringTraining();
		for (TrainingItem trainingItem : batchTrainingItems) {
			Vector actualOutput = feedForward(trainingItem.getInput());
			Vector outputDifference = subtract(actualOutput, trainingItem.getExpectedOutput());
			

			Vector outputError = scale(outputDifference, x -> 1.0 / 2.0 * Math.pow(x, 2));
			double batchError = sum(outputError);
			progress.accumulateBatchError(batchError);
		
		}
		
//		Vector actualOutput = network.evaluate(example.getInput());
//		Vector outputDifference = getMathOps().subtract(actualOutput, example.getExpectedOutput());

//		Vector outputError = getMathOps().scale(outputDifference, x -> 1.0 / 2.0 * Math.pow(x, 2));
//		double batchError = sum(outputError);
//		progress.accumulateBatchError(batchError);

//		Vector input = network.getInput(getLayerCount() - 1);
//
//		Vector primeOutput = network.getBiasedPrimeOutput(getLayerCount() - 1);
//		Vector outputDelta = getMathOps().hadamard(outputDifference, primeOutput);
//		network.setOutputDelta(getLayerCount() - 1, outputDelta);
//
//		for (int l = getLayerCount() - 2; l >= 0; l--) {
//			Matrix nextLayerWeights = network.getWeights(l + 1);
//			Matrix transposedNextLayerWeights = getMathOps().transpose(nextLayerWeights);
//			Vector nextLayerOutputDelta = network.getOutputDelta(l + 1);
//			Vector weightsDelta = getMathOps().multiply(transposedNextLayerWeights, nextLayerOutputDelta);
//			primeOutput = network.getBiasedPrimeOutput(l);
//			outputDelta = getMathOps().hadamard(weightsDelta, primeOutput);
//			network.setOutputDelta(l, outputDelta);
//		}
//
//		for (int l = 0; l < getLayerCount(); l++) {
//			outputDelta = network.getOutputDelta(l);
//			input = network.getInput(l);
//			Matrix transposedInput = getMathOps().transpose(input);
//			Matrix weightError = getMathOps().multiply(outputDelta, transposedInput);
//
//			Matrix scaledWeightError = getMathOps().scale(weightError, x -> x * getLearningRate(progress));
//
//			Matrix currentWeights = network.getWeights(l);
//
//			Matrix newWeights = getMathOps().subtract(currentWeights, scaledWeightError);
//			network.setWeights(l, newWeights);
//
//			Vector scaledOutputDelta = getMathOps().scale(outputDelta, x -> x * getLearningRate(progress));
//			Vector currentBias = network.getBias(l);
//			Vector newBias = getMathOps().subtract(currentBias, scaledOutputDelta);
//			network.setBias(l, newBias);
//		}		
		
	}

	private void train(TrainingItem example, TrainingProgress progress) {
		Vector actualOutput = feedForward(example.getInput());
		Vector outputDifference = subtract(actualOutput, example.getExpectedOutput());

		Vector outputError = scale(outputDifference, x -> 1.0 / 2.0 * Math.pow(x, 2));
		double batchError = sum(outputError);
		progress.accumulateBatchError(batchError);

		Vector input = getNetworkInput(getLayerCount() - 1);

		Vector primeOutput = getNetworkBiasedPrimeOutput(getLayerCount() - 1);
		Vector outputDelta = hadamard(outputDifference, primeOutput);
		setNetworkOutputDelta(getLayerCount() - 1, outputDelta);

		for (int l = getLayerCount() - 2; l >= 0; l--) {
			Matrix nextLayerWeights = getNetworkWeights(l + 1);
			Matrix transposedNextLayerWeights = transpose(nextLayerWeights);
			Vector nextLayerOutputDelta = getNetworkOutputDelta(l + 1);
			Vector weightsDelta = multiply(transposedNextLayerWeights, nextLayerOutputDelta);
			primeOutput = getNetworkBiasedPrimeOutput(l);
			outputDelta = hadamard(weightsDelta, primeOutput);
			setNetworkOutputDelta(l, outputDelta);
		}

		for (int l = 0; l < getLayerCount(); l++) {
			outputDelta = getNetworkOutputDelta(l);
			input = getNetworkInput(l);
			Matrix transposedInput = transpose(input);
			Matrix weightError = multiply(outputDelta, transposedInput);

			Matrix scaledWeightError = scale(weightError, x -> x * getLearningRate(progress));

			Matrix currentWeights = getNetworkWeights(l);

			Matrix newWeights = subtract(currentWeights, scaledWeightError);
			setNetworkWeights(l, newWeights);

			Vector scaledOutputDelta = scale(outputDelta, x -> x * getLearningRate(progress));
			Vector currentBias = getNetworkBias(l);
			Vector newBias = subtract(currentBias, scaledOutputDelta);
			setNetworkBias(l, newBias);
		}
	}
	
}
