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

	protected int getLayerCount() {
		return config.getLayerCount();
	}

	protected InspectableMatrix getOutput(int layerIndex) {
		return getLayers().get(layerIndex).getOutput();
	}

	protected Matrix getBiasedPrimeOutput(int layerIndex) {
		return getLayers().get(layerIndex).getBiasedPrimeOutput();
	}

	protected Matrix getWeights(int layerIndex) {
		return getLayers().get(layerIndex).getWeights();
	}

	protected void setWeights(int layerIndex, Matrix weights) {
		getLayers().get(layerIndex).setWeights(weights);
	}

	protected RowVector getBias(int layerIndex) {
		return getLayers().get(layerIndex).getBias();
	}

	protected void setBias(int layerIndex, RowVector bias) {
		getLayers().get(layerIndex).setBias(bias);
	}

	protected Function getActivationFunction(int layerIndex) {
		return getLayers().get(layerIndex).getActivationFunction();
	}

	protected Function getActivationPrimeFunction(int layerIndex) {
		return getLayers().get(layerIndex).getActivationPrimeFunction();
	}

	protected Matrix getOutputDelta(int layerIndex) {
		return getLayers().get(layerIndex).getOutputDelta();
	}

	protected void setOutputDelta(int layerIndex, Matrix outputDelta) {
		getLayers().get(layerIndex).setOutputDelta(outputDelta);
	}

	protected void setAccumulateDuringTraining(boolean accumulateDuringTraining) {
		for (FeedForwardNetworkLayer layer : getLayers()) {
			layer.setAccumulateDuringTraining(accumulateDuringTraining);
		}
	}

	protected void resetAccumulationDuringTraining() {
		for (FeedForwardNetworkLayer layer : getLayers()) {
			layer.resetAccumulationDuringTraining();
		}
	}
	
	public InspectableMatrix getInput(int layerIndex) {
		return getLayers().get(layerIndex).getInput();
	}

	public void setTraining(boolean isTraining) {
		for (FeedForwardNetworkLayer layer : getLayers()) {
			layer.setTraining(isTraining);
		}
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
	
	protected Context getContext() {
		return config.getContext();
	}
	
	private EventManager getEventManager() {
		return config.getEventManager();
	}
	
	private List<FeedForwardNetworkLayer> getLayers() {
		return config.getLayers();
	}

}