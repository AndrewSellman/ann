package com.sellman.andrew.ann.core.math;

class FixedValueFunction implements Function {
	protected static final double FIXED_VALUE = 42.0;
	
	@Override
	public double evaluate(double x) {
		return FIXED_VALUE;
	}

}
