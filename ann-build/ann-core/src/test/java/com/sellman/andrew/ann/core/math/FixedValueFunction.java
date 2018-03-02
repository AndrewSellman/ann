package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.math.function.Function;

public class FixedValueFunction implements Function {
	public static final double FIXED_VALUE = 42.0;
	
	@Override
	public double evaluate(double x) {
		return FIXED_VALUE;
	}

}
