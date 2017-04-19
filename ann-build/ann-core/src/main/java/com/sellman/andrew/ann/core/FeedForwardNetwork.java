package com.sellman.andrew.ann.core;

import java.util.List;

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

}
