package com.sellman.andrew.ann.core;

import java.util.List;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetwork {
	private final String name;
	private final List<FeedForwardLayer> layers;

	public FeedForwardNetwork(String name, List<FeedForwardLayer> layers) {
		this.name = name;
		this.layers = layers;
	}

	public Vector evaluate(final Vector input) {
		Vector nextLayerInput = input;
		Vector output = null;
		for (FeedForwardLayer layer : layers) {
			output = layer.evaluate(nextLayerInput);
			nextLayerInput = output;
		}

		return output;
	}

	protected int getLayerCount() {
		return layers.size();
	}

	protected Vector getOutput(int layerIndex) {
		return layers.get(layerIndex).getOutput();
	}

	protected Vector getWeightedInput(int layerIndex) {
		return layers.get(layerIndex).getWeightedInput();
	}

	protected Vector getBias(int layerIndex) {
		return layers.get(layerIndex).getBias();
	}

	protected Matrix getWeights(int layerIndex) {
		return layers.get(layerIndex).getWeights();
	}

	protected Function getActivationFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationFunction();
	}

	protected Function getActivationPrimeFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationPrimeFunction();
	}

	protected Vector getOutputError(int layerIndex) {
		return layers.get(layerIndex).getOutputError();
	}

	protected void setOutputError(int layerIndex, Vector outputError) {
		layers.get(layerIndex).setOutputError(outputError);
	}

}
