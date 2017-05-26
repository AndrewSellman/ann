package com.sellman.andrew.ann.core.math;

class LogisticPrimeFunction implements Function {
	private static final Function DELEGATE = new LogisticFunction();
	
	@Override
	public double evaluate(double x) {
		return DELEGATE.evaluate(-x);
	}

}
