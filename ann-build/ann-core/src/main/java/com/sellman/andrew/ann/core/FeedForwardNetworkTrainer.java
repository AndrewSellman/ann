package com.sellman.andrew.ann.core;

import java.util.List;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetworkTrainer {
	private final FeedForwardNetwork network;
	private final MatrixOperations matrixOperations;

	public FeedForwardNetworkTrainer(final FeedForwardNetwork network, MatrixOperations matrixOperations) {
		this.network = network;
		this.matrixOperations = matrixOperations;
	}

	public void train(List<TrainingData> trainingData) {
		for (TrainingData trainingElement : trainingData) {
			Vector actualOutput = network.evaluate(trainingElement.getInput());
			Vector expectedOutput = trainingElement.getExpectedOutput();
			Vector networkOutputError = calculateNetworkOutputError(actualOutput, expectedOutput);
			network.setOutputError(network.getLayerCount() - 1, networkOutputError);

			for (int l = network.getLayerCount() - 2; l >= 0; l--) {
				Matrix nextLayerWeights = network.getWeights(l + 1);
				Matrix transposedNextLayerWeights = matrixOperations.transpose(nextLayerWeights);
				Vector nextLayerError = network.getOutputError(l + 1);
				Vector propagatedError = matrixOperations.multiply(nextLayerError, transposedNextLayerWeights);

				Vector weightedInput = network.getWeightedInput(l);
				Function primeActivationFunction = network.getActivationPrimeFunction(l);
				Vector primeWeightedInput = matrixOperations.scale(weightedInput, primeActivationFunction);

				Vector outputError = matrixOperations.hadamard(propagatedError, primeWeightedInput);
				network.setOutputError(l, outputError);
			}
		}
	}

	private Vector calculateNetworkOutputError(Vector actualOutput, Vector expectedOutput) {
		Vector rawDifference = matrixOperations.subtract(actualOutput, expectedOutput);
		Vector weightedInput = network.getWeightedInput(network.getLayerCount() - 1);
		Function primeActivationFunction = network.getActivationPrimeFunction(network.getLayerCount() - 1);
		Vector primeWeightedInput = matrixOperations.scale(weightedInput, primeActivationFunction);
		return matrixOperations.hadamard(rawDifference, primeWeightedInput);
	}

}
