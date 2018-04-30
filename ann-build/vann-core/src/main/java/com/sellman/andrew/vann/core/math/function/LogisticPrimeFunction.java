package com.sellman.andrew.vann.core.math.function;

class LogisticPrimeFunction extends AbstractFunction {
	private final LogisticFunction f;

	public LogisticPrimeFunction() {
		super(LogisticPrimeFunction.class.toString());
		f = new LogisticFunction();
	}

	@Override
	public double evaluate(double x) {
		double intermediate = f.evaluate(x);
		return intermediate * (1.0 - intermediate);
	}

}
