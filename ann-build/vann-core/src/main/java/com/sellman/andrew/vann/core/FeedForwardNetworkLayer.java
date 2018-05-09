package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerInputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputEvent;
import com.sellman.andrew.vann.core.event.NetworkLayerWeightedInputEvent;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.math.function.Function;

public class FeedForwardNetworkLayer {
	private final FeedForwardNetworkLayerConfig config;
	private Matrix input;
	private Matrix output;
	private Matrix biasedPrimeOutput;
	private Matrix outputDelta;
	private boolean training;
	private boolean accumulateDuringTraining;
	private Matrix accumulatedOutputDelta;
	private Matrix accumulatedBiasedPrimeOutput;
	
	public FeedForwardNetworkLayer(final FeedForwardNetworkLayerConfig config) {
		this.config = config;
	}

	public Matrix evaluate(Matrix input) {
		fireNetworkLayerInputEvent(input);
		
		Matrix weightedInput = multiply(input, config.getWeights());
		fireNetworkLayerWeightedInputEvent(weightedInput);
		
		Matrix biasedWeightedInput = add(weightedInput, config.getBias());
		fireNetworkLayerBiasedWeightedInputEvent(biasedWeightedInput);
		
		Matrix output = scale(biasedWeightedInput, config.getActivationFunction());

		//TODO move to relevant listeners	
		if (isTraining()) {
			this.input = input;
			this.output = output;
			biasedPrimeOutput = scale(biasedWeightedInput, config.getActivationPrimeFunction());
			if (accumulateDuringTraining) {
				accumulatedBiasedPrimeOutput = add(accumulatedBiasedPrimeOutput, accumulatedBiasedPrimeOutput);
			}
		}

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

	private void fireNetworkLayerInputEvent(Matrix input) {
		if (!isAnyListener(NetworkLayerInputEvent.class)) {
			return;
		}

		NetworkLayerInputEvent event = new NetworkLayerInputEvent(getContext(), input);
		fire(event);
	}
	
	private void fireNetworkLayerOutputEvent(Matrix output) {
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


	protected Matrix getOutput() {
		return output;
	}

	protected Matrix getBiasedPrimeOutput() {
		return biasedPrimeOutput;
	}

	protected Matrix getWeights() {
		return config.getWeights();
	}

	protected void setWeights(Matrix weights) {
		getMatrixOps().update(weights, getWeights());
	}

	protected Matrix getInput() {
		return input;
	}

	protected Function getActivationFunction() {
		return config.getActivationFunction();
	}

	protected Function getActivationPrimeFunction() {
		return config.getActivationPrimeFunction();
	}

	protected Matrix getOutputDelta() {
		return outputDelta;
	}

	protected void setOutputDelta(Matrix outputDelta) {
		this.outputDelta = outputDelta;
		if (training && accumulateDuringTraining) {
			accumulatedOutputDelta = add(accumulatedOutputDelta, outputDelta);
		}
	}

	protected Matrix getAccumulatedOutputDelta() {
		return accumulatedOutputDelta;
	}

	protected Matrix getAccumulatedBiasedPrimeOutput() {
		return accumulatedBiasedPrimeOutput;
	}

	protected boolean isTraining() {
		return training;
	}

	protected void setTraining(boolean isTraining) {
		this.training = isTraining;
		
		if (!training) {
			input = null;
			biasedPrimeOutput = null;
			output = null;
			outputDelta = null;
		}
	}

	protected Vector getBias() {
		return config.getBias();
	}
	
	protected void setBias(Vector bias) {
		getMatrixOps().update(bias, getBias());
	}


	private MathOperations getMatrixOps() {
		return config.getMatrixOps();
	}

	private Matrix multiply(Matrix left, Matrix right) {
		return getMatrixOps().multiply(left, right);
	}

	private Matrix add(Matrix a, Matrix b) {
		return getMatrixOps().add(a, b);
	}

	private Matrix add(Matrix m, Vector v) {
		return getMatrixOps().add(m, v);
	}

	private Matrix scale(Matrix m, Function f) {
		return getMatrixOps().scale(m, f);
	}

	protected void setAccumulateDuringTraining(boolean accumulateDuringTraining) {
		this.accumulateDuringTraining = accumulateDuringTraining;
		if (!accumulateDuringTraining) {
			accumulatedOutputDelta = null;
			accumulatedBiasedPrimeOutput = null;
		}
	}

	protected void resetAccumulationDuringTraining() {
		int rowCount = config.getWeights().getRowCount();
		int columnCount = config.getWeights().getColumnCount();
		accumulatedOutputDelta = new Matrix(rowCount, columnCount);
		accumulatedBiasedPrimeOutput = new Matrix(rowCount, columnCount);
	}

}
