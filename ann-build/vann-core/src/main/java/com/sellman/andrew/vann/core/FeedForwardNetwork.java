package com.sellman.andrew.vann.core;

import java.util.List;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.NetworkInputEvent;
import com.sellman.andrew.vann.core.event.NetworkOutputEvent;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;

public class FeedForwardNetwork {
	private final FeedForwardNetworkConfig config;

	public FeedForwardNetwork(FeedForwardNetworkConfig config) {
		this.config = config;
	}

	public InspectableMatrix evaluate(final InspectableMatrix input) {
		fireNetworkInputEvent(input);

		InspectableMatrix nextLayerInput = input;
		Matrix output = null;
		for (FeedForwardNetworkLayer layer : getLayers()) {
			output = layer.evaluate(nextLayerInput);
			nextLayerInput = output;
		}

		fireNetworkOutputEvent(output);
		return output;
	}

	public int getLayerCount() {
		return config.getLayerCount();
	}

	public Matrix getWeights(int layerIndex) {
		return getLayers().get(layerIndex).getWeights();
	}

	public void setWeights(int layerIndex, Matrix weights) {
		getLayers().get(layerIndex).setWeights(weights);
	}

	public RowVector getBias(int layerIndex) {
		return getLayers().get(layerIndex).getBias();
	}

	public void setBias(int layerIndex, RowVector bias) {
		getLayers().get(layerIndex).setBias(bias);
	}

	protected Function getActivationFunction(int layerIndex) {
		return getLayers().get(layerIndex).getActivationFunction();
	}

	public Function getActivationPrimeFunction(int layerIndex) {
		return getLayers().get(layerIndex).getActivationPrimeFunction();
	}

	private void fireNetworkInputEvent(InspectableMatrix input) {
		if (!isAnyListener(NetworkInputEvent.class)) {
			return;
		}

		NetworkInputEvent event = new NetworkInputEvent(getContext(), input);
		fire(event);
	}

	private void fireNetworkOutputEvent(Matrix output) {
		if (!isAnyListener(NetworkOutputEvent.class)) {
			return;
		}

		NetworkOutputEvent event = new NetworkOutputEvent(getContext(), output);
		fire(event);
	}

	private void fire(Event event) {
		getEventManager().fire(event);
	}

	private boolean isAnyListener(Class<? extends Event> eventType) {
		return getEventManager().isAnyRegisteredListenerFor(eventType);
	}
	
	public Context getContext() {
		return config.getContext();
	}
	
	private EventManager getEventManager() {
		return config.getEventManager();
	}
	
	private List<FeedForwardNetworkLayer> getLayers() {
		return config.getLayers();
	}

}
