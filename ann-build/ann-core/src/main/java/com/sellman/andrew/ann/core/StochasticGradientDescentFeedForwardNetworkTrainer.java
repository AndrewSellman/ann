package com.sellman.andrew.ann.core;

import java.util.List;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.TrainingProgress;

public class StochasticGradientDescentFeedForwardNetworkTrainer extends AbstractFeedForwardNetworkTrainer {

	public StochasticGradientDescentFeedForwardNetworkTrainer(final FeedForwardNetworkTrainerConfig config, final FeedForwardNetwork network) {
		super(config, network);
	}

	protected final void trainBatch(List<TrainingItem> batchTrainingItems, TrainingProgress progress) {
		for (TrainingItem trainingItem : batchTrainingItems) {
			train(trainingItem, progress);
		}
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

	protected final List<TrainingItem> getBatchTrainingItems(List<TrainingItem> trainingData, TrainingProgress progress) {
		return trainingData;
	}

	protected final int getBatchCount(int traingDataSize, int batchSize) {
		return 1;
	}

}
