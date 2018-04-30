package com.sellman.andrew.ann.core.math.function;

class LogisticFunction extends AbstractFunction {

	public LogisticFunction() {
		super(LogisticFunction.class.toString());
	}

	@Override
	public double evaluate(double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, -x));
	}

}
