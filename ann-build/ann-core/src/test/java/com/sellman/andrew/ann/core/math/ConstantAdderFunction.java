package com.sellman.andrew.ann.core.math;

public class ConstantAdderFunction implements Function {
	private final double constant;

	public ConstantAdderFunction(double constant) {
		this.constant = constant;
	}

	@Override
	public double evaluate(double x) {
		return x + constant;
	}

}
