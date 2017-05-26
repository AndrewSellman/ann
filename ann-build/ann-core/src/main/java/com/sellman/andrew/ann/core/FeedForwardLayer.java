package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardLayer {
	private final String name;
	private final MatrixOperations matrixOperations;
	private final Matrix weights;
	private final Vector bias;
	private final FunctionGroup activationFunctionGroup;
	private Vector weightedInput;
	private Vector output;
	private Vector outputError;
	
	public FeedForwardLayer(String name, MatrixOperations matrixOperations, Matrix weights, Vector bias, FunctionGroup activationFunctionGroup) {
		this.name = name;
		this.matrixOperations = matrixOperations;
		this.weights = weights;
		this.bias = bias;
		this.activationFunctionGroup = activationFunctionGroup;
	}

	public Vector evaluate(Vector input) {
		weightedInput = matrixOperations.add(matrixOperations.multiply(input, weights), bias);
		output = matrixOperations.scale(weightedInput, activationFunctionGroup.getFunction());
		return output;
	}
	
	protected Vector getBias() {
		return bias;
	}

	protected Vector getOutput() {
		return output;
	}

	protected Matrix getWeights() {
		return weights;
	}

	protected Vector getWeightedInput() {
		return weightedInput;
	}
	
	protected Function getActivationFunction() {
		return activationFunctionGroup.getFunction();
	}

	protected Function getActivationPrimeFunction() {
		return activationFunctionGroup.getPrime();
	}

	protected Vector getOutputError() {
		return outputError;
	}

	protected void setOutputError(Vector outputError) {
		this.outputError = outputError;
	}

}
