package com.sellman.andrew.ann.core.math;

class LogisticFunction implements Function {

	@Override
	public double evaluate(double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, -x));
	}

}
