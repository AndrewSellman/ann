package com.sellman.andrew.ann.core.math.function;

class LogisticPrimeFunction extends LogisticFunction {
	
	@Override
	public double evaluate(double x) {
		double intermediate = super.evaluate(x);
		return intermediate * (1.0 - intermediate);
	}

}
