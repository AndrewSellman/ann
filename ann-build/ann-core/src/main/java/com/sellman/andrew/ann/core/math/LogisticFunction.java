package com.sellman.andrew.ann.core.math;

public class LogisticFunction implements Function {

	public double evaluate(double input) {
		return 1 / (1 + Math.pow(Math.E, -input));
	}

}
