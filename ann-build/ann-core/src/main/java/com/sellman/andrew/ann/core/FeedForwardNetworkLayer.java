package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetworkLayer {
	private final FeedForwardNetworkLayerConfig config;
	private Vector input;
	private Vector output;
	private Vector biasedPrimeOutput;
	private Vector outputDelta;
	private boolean training;

	public FeedForwardNetworkLayer(final FeedForwardNetworkLayerConfig config) {
		this.config = config;
	}

	public Vector evaluate(Vector input) {
		Vector biasedWeightedInput = add(multiply(config.getWeights(), input), config.getBias());
		Vector output = scale(biasedWeightedInput, config.getActivationFunction());

		if (isTraining()) {
			this.input = input;
			this.output = output;
			biasedPrimeOutput = scale(biasedWeightedInput, config.getActivationPrimeFunction());
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


	private MatrixOperations getMatrixOps() {
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

}
