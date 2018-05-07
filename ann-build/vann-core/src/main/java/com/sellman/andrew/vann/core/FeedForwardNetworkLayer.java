package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.math.function.Function;

public class FeedForwardNetworkLayer {
	private final FeedForwardNetworkLayerConfig config;
	private Vector input;
	private Vector output;
	private Vector biasedPrimeOutput;
	private Vector outputDelta;
	private boolean training;
	private boolean accumulateDuringTraining;
	private Vector accumulatedOutputDelta;
	private Vector accumulatedBiasedPrimeOutput;
	
	public FeedForwardNetworkLayer(final FeedForwardNetworkLayerConfig config) {
		this.config = config;
	}

	public Vector evaluate(Vector input) {
//TODO fire input/output events
		
		Vector biasedWeightedInput = add(multiply(config.getWeights(), input), config.getBias());
		Vector output = scale(biasedWeightedInput, config.getActivationFunction());

		if (isTraining()) {
			this.input = input;
			this.output = output;
			biasedPrimeOutput = scale(biasedWeightedInput, config.getActivationPrimeFunction());
			if (accumulateDuringTraining) {
				accumulatedBiasedPrimeOutput = add(accumulatedBiasedPrimeOutput, accumulatedBiasedPrimeOutput);
			}
		}

		return output;
	}

	protected Vector getOutput() {
		return output;
	}

	protected Vector getBiasedPrimeOutput() {
		return biasedPrimeOutput;
	}

	protected Matrix getWeights() {
		return config.getWeights();
	}

	protected void setWeights(Matrix weights) {
		getMatrixOps().update(weights, getWeights());
	}

	protected Vector getInput() {
		return input;
	}

	protected Function getActivationFunction() {
		return config.getActivationFunction();
	}

	protected Function getActivationPrimeFunction() {
		return config.getActivationPrimeFunction();
	}

	protected Vector getOutputDelta() {
		return outputDelta;
	}

	protected void setOutputDelta(Vector outputDelta) {
		this.outputDelta = outputDelta;
		if (training && accumulateDuringTraining) {
			accumulatedOutputDelta = add(accumulatedOutputDelta, outputDelta);
		}
	}

	protected Vector getAccumulatedOutputDelta() {
		return accumulatedOutputDelta;
	}

	protected Vector getAccumulatedBiasedPrimeOutput() {
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

	private Vector multiply(Matrix m, Vector v) {
		return getMatrixOps().multiply(m, v);
	}

	private Vector add(Vector a, Vector b) {
		return getMatrixOps().add(a, b);
	}

	private Vector scale(Vector v, Function f) {
		return getMatrixOps().scale(v, f);
	}

	protected void setAccumulateDuringTraining(boolean accumulateDuringTraining) {
		this.accumulateDuringTraining = accumulateDuringTraining;
		if (!accumulateDuringTraining) {
			accumulatedOutputDelta = null;
			accumulatedBiasedPrimeOutput = null;
		}
	}

	protected void resetAccumulationDuringTraining() {
		accumulatedOutputDelta = new Vector(config.getWeights().getColumnCount());
		accumulatedBiasedPrimeOutput = new Vector(config.getWeights().getColumnCount());
	}

}
