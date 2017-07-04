package com.sellman.andrew.ann.core;

import java.util.List;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetwork {
	private final String name;
	private final List<FeedForwardLayer> layers;

	public FeedForwardNetwork(String name, List<FeedForwardLayer> layers) {
		this.name = name;
		this.layers = layers;
	}

	public Matrix evaluate(final Matrix input) {
		Matrix nextLayerInput = input;
		Matrix output = null;
		for (FeedForwardLayer layer : layers) {
			output = layer.evaluate(nextLayerInput);
			nextLayerInput = output;
		}

		return output;
	}

	protected int getLayerCount() {
		return layers.size();
	}

	protected Matrix getOutput(int layerIndex) {
		return layers.get(layerIndex).getOutput();
	}

	protected Matrix getBiasedPrimeOutput(int layerIndex) {
		return layers.get(layerIndex).getBiasedPrimeOutput();
	}

	protected Matrix getUnbiasedWeightedInput(int layerIndex) {
		return layers.get(layerIndex).getUnbiasedWeightedInput();
	}

	protected Matrix getBiasedWeightedInput(int layerIndex) {
		return layers.get(layerIndex).getBiasedWeightedInput();
	}

	protected Matrix getWeights(int layerIndex) {
		return layers.get(layerIndex).getWeights();
	}

	protected Matrix getBias(int layerIndex) {
		return layers.get(layerIndex).getBias();
	}

	protected Function getActivationFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationFunction();
	}

	protected Function getActivationPrimeFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationPrimeFunction();
	}

	protected Matrix getTransposedOutputError(int layerIndex) {
		return layers.get(layerIndex).getTransposedOutputError();
	}

	protected void setTransposedOutputError(int layerIndex, Matrix transposedOutputError) {
		layers.get(layerIndex).setTransposedOutputError(transposedOutputError);
	}

	protected Matrix getOutputDelta(int layerIndex) {
		return layers.get(layerIndex).getOutputDelta();
	}

	protected void setOutputDelta(int layerIndex, Matrix outputDelta) {
		layers.get(layerIndex).setOutputDelta(outputDelta);
	}

	public Matrix getInput(int layerIndex) {
		return layers.get(layerIndex).getInput();
	}

	public void setTraining(boolean isTraining) {
		for (FeedForwardLayer layer : layers) {
			layer.setTraining(isTraining);
		}
	}

}
