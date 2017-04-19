package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.MatrixAdder;
import com.sellman.andrew.ann.core.math.Function;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixMultiplier;
import com.sellman.andrew.ann.core.math.MatrixScaler;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardLayer {
	private final String name;
	private final MatrixMultiplier multiplier;
	private final MatrixAdder adder;
	private final MatrixScaler scaler;
	private final Matrix weight;
	private final Vector bias;
	private final Function activationFunction;

	public FeedForwardLayer(String name, MatrixMultiplier multiplier, MatrixAdder adder, MatrixScaler scaler, Matrix weight, Vector bias, Function activationFunction) {
		this.name = name;
		this.multiplier = multiplier;
		this.adder = adder;
		this.scaler = scaler;
		this.weight = weight;
		this.bias = bias;
		this.activationFunction = activationFunction;
	}

	public Vector evaluate(Vector input) {
		Vector weightedInput = adder.add(multiplier.multiply(input, weight), bias);
		return scaler.scale(weightedInput, activationFunction);
	}
	
}
