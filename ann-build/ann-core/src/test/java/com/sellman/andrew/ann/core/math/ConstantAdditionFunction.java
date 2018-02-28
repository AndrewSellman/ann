package com.sellman.andrew.ann.core.math;

public class ConstantAdditionFunction implements Function {
	private final double constant;

	public ConstantAdditionFunction(double constant) {
		this.constant = constant;
	}

	@Override
	public double evaluate(double x) {
		return x + constant;
	}

}
