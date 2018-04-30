package com.sellman.andrew.ann.core.math.function;

public class FixedValueFunction extends AbstractFunction {
	public static final double FIXED_VALUE = 42.0;
	
	public FixedValueFunction() {
		super(FixedValueFunction.class.toString() + FIXED_VALUE);
	}
	
	@Override
	public double evaluate(double x) {
		return FIXED_VALUE;
	}

}
