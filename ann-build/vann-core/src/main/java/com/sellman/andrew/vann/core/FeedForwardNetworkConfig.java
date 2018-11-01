package com.sellman.andrew.vann.core;

import java.util.List;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;

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

	protected Matrix getWeights(int layerIndex) {
		return layers.get(layerIndex).getWeights();
	}

	protected RowVector getBias(int layerIndex) {
		return layers.get(layerIndex).getBias();
	}

	protected Function getActivationFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationFunction();
	}

	protected Function getActivationPrimeFunction(int layerIndex) {
		return layers.get(layerIndex).getActivationPrimeFunction();
	}

	protected Context getContext() {
		return context;
	}

	protected EventManager getEventManager() {
		return eventManager;
	}

}
