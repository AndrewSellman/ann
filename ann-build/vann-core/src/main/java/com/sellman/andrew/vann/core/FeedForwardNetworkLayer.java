package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerWeightedInputEvent;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;

public class FeedForwardNetworkLayer {
	private final FeedForwardNetworkLayerConfig config;
	
	public FeedForwardNetworkLayer(final FeedForwardNetworkLayerConfig config) {
		this.config = config;
	}

	public Matrix evaluate(InspectableMatrix input) {
		fireNetworkLayerInputEvent(input);
		
		Matrix weightedInput = multiply(input, config.getWeights());
		fireNetworkLayerWeightedInputEvent(weightedInput);
		
		Matrix biasedWeightedInput = add(weightedInput, config.getBias());
		fireNetworkLayerBiasedWeightedInputEvent(biasedWeightedInput);
		
		Matrix output = scale(biasedWeightedInput, config.getActivationFunction());
		fireNetworkLayerOutputEvent(input);

		return output;
	}

	private void fireNetworkLayerBiasedWeightedInputEvent(Matrix biasedWeightedInput) {
		if (!isAnyListener(NetworkLayerBiasedWeightedInputEvent.class)) {
			return;
		}

		NetworkLayerBiasedWeightedInputEvent event = new NetworkLayerBiasedWeightedInputEvent(getContext(), biasedWeightedInput);
		fire(event);
	}

	private void fireNetworkLayerWeightedInputEvent(Matrix weightedInput) {
		if (!isAnyListener(NetworkLayerWeightedInputEvent.class)) {
			return;
		}

		NetworkLayerWeightedInputEvent event = new NetworkLayerWeightedInputEvent(getContext(), weightedInput);
		fire(event);
	}

	private void fireNetworkLayerInputEvent(InspectableMatrix input) {
		if (!isAnyListener(NetworkLayerInputEvent.class)) {
			return;
		}

		NetworkLayerInputEvent event = new NetworkLayerInputEvent(getContext(), input);
		fire(event);
	}
	
	private void fireNetworkLayerOutputEvent(InspectableMatrix output) {
		if (!isAnyListener(NetworkLayerOutputEvent.class)) {
			return;
		}

		NetworkLayerOutputEvent event = new NetworkLayerOutputEvent(getContext(), output);
		fire(event);
	}

	private void fire(Event event) {
		getEventManager().fire(event);
	}
	
	private boolean isAnyListener(Class<? extends Event> eventType) {
		return getEventManager().isAnyRegisteredListenerFor(eventType);
	}
	
	private Context getContext() {
		return config.getContext();
	}

	
	private EventManager getEventManager() {
		return config.getEventManager();
	}


	protected Matrix getWeights() {
		return config.getWeights();
	}

	protected void setWeights(Matrix weights) {
		getMathOps().update(weights, getWeights());
	}

	protected Function getActivationFunction() {
		return config.getActivationFunction();
	}

	protected Function getActivationPrimeFunction() {
		return config.getActivationPrimeFunction();
	}

	protected RowVector getBias() {
		return config.getBias();
	}
	
	protected void setBias(RowVector bias) {
		getMathOps().update(bias, getBias());
	}

	private Matrix multiply(InspectableMatrix left, InspectableMatrix right) {
		return getMathOps().multiply(left, right);
	}

	private Matrix add(Matrix m, RowVector v) {
		InspectableMatrix vm = config.getMatrixFactory().createFor(v, m.getRowCount());
		return getMathOps().add(m, vm);
	}

	private Matrix scale(Matrix m, Function f) {
		return getMathOps().scale(m, f);
	}

	private MathOperations getMathOps() {
		return config.getMathOps();
	}

}
