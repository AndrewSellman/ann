package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;

public class FeedForwardLayer {
	private final String name;
	private final MatrixOperations matrixOperations;
	private final Matrix weights;
	private final Matrix bias;
	private final FunctionGroup activationFunctionGroup;
	private Matrix input;
	private Matrix biasedWeightedInput;
	private Matrix unbiasedWeightedInput;
	private Matrix output;
	private Matrix biasedPrimeOutput;
	private Matrix transposedOutputError;
	private Matrix outputDelta;
	private boolean training;

	public FeedForwardLayer(final String name, final MatrixOperations matrixOperations, final Matrix weights, final Matrix bias, final FunctionGroup activationFunctionGroup) {
		this.name = name;
		this.matrixOperations = matrixOperations;
		this.weights = weights;
		this.bias = bias;
		this.activationFunctionGroup = activationFunctionGroup;
	}

	public Matrix evaluate(Matrix input) {
		Matrix unbiasedWeightedInput = matrixOperations.multiply(weights, input);
		Matrix biasedWeightedInput = matrixOperations.add(unbiasedWeightedInput, bias);
		Matrix output = matrixOperations.scale(biasedWeightedInput, activationFunctionGroup.getFunction());

		if (isTraining()) {
			this.input = input;
			this.biasedWeightedInput = biasedWeightedInput;
			this.output = output;
			biasedPrimeOutput = matrixOperations.scale(biasedWeightedInput, activationFunctionGroup.getPrime());
		}

		return output;
	}

	protected Matrix getOutput() {
		return output;
	}

	protected Matrix getBiasedPrimeOutput() {
		return biasedPrimeOutput;
	}

	protected Matrix getWeights() {
		return weights;
	}

	protected void setWeights(Matrix weights) {
		matrixOperations.update(weights, this.weights);
	}

	protected Matrix getInput() {
		return input;
	}

	protected Matrix getBiasedWeightedInput() {
		return biasedWeightedInput;
	}

	protected Matrix getUnbiasedWeightedInput() {
		return unbiasedWeightedInput;
	}

	protected Function getActivationFunction() {
		return activationFunctionGroup.getFunction();
	}

	protected Function getActivationPrimeFunction() {
		return activationFunctionGroup.getPrime();
	}

	protected Matrix getTransposedOutputError() {
		return transposedOutputError;
	}

	protected void setTransposedOutputError(Matrix transposedOutputError) {
		this.transposedOutputError = transposedOutputError;
	}

	protected Matrix getOutputDelta() {
		return outputDelta;
	}

	protected void setOutputDelta(Matrix outputDelta) {
		this.outputDelta = outputDelta;
	}

	protected boolean isTraining() {
		return training;
	}

	protected void setTraining(boolean training) {
		this.training = training;
	}

	protected Matrix getBias() {
		return bias;
	}

}
