package com.sellman.andrew.ann.core.math.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogisticPrimeFunctionTest {
	private static final double INPUT = 0.3775;
	
	private Function function = new LogisticPrimeFunction();

	@Test
	public void evaluate() {
		double result = function.evaluate(INPUT);
		assertEquals(0.24130070859, result, 0.000000001);
	}

}
