package com.sellman.andrew.ann.core.math.function;

public class ConstantAdditionFunction extends AbstractFunction {
	private final double constant;

	public ConstantAdditionFunction(double constant) {
		super(ConstantAdditionFunction.class.toString() + constant);
		this.constant = constant;
	}

	@Override
	public double evaluate(double x) {
		return x + constant;
	}

}
