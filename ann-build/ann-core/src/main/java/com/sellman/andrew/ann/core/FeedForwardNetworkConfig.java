package com.sellman.andrew.ann.core;

import java.util.List;

import com.sellman.andrew.ann.core.event.Context;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetworkConfig {
	private final Context context;
	private final EventManager eventManager;
	private final List<FeedForwardNetworkLayer> layers;

	public FeedForwardNetworkConfig(Context context, EventManager eventManager, List<FeedForwardNetworkLayer> layers) {
		this.context = context;
		this.eventManager = eventManager;
		this.layers = layers;
	}

	protected int getLayerCount() {
		return layers.size();
	}

	protected List<FeedForwardNetworkLayer> getLayers() {
		return layers;
	}

	protected Vector getOutput(int layerIndex) {
		return layers.get(layerIndex).getOutput();
	}

	protected Vector getBiasedPrimeOutput(int layerIndex) {
		return layers.get(layerIndex).getBiasedPrimeOutput();
	}

	protected Matrix getWeights(int layerIndex) {
		return layers.get(layerIndex).getWeights();
	}

	protected Vector getBias(int layerIndex) {
		return layers.get(layerIndex).getBias();
	}

	protected Function getActivationFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationFunction();
	}

	protected Function getActivationPrimeFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationPrimeFunction();
	}

	protected Vector getOutputDelta(int layerIndex) {
		return layers.get(layerIndex).getOutputDelta();
	}

	protected void setOutputDelta(int layerIndex, Vector outputDelta) {
		layers.get(layerIndex).setOutputDelta(outputDelta);
	}

	public Vector getInput(int layerIndex) {
		return layers.get(layerIndex).getInput();
	}

	public void setTraining(boolean isTraining) {
		for (FeedForwardNetworkLayer layer : layers) {
			layer.setTraining(isTraining);
		}
	}

	protected Context getContext() {
		return context;
	}

	protected EventManager getEventManager() {
		return eventManager;
	}

}
