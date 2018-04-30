package com.sellman.andrew.vann.core.math.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.function.LogisticPrimeFunction;

public class LogisticPrimeFunctionTest {
	private static final double INPUT = 0.3775;
	
	private Function function = new LogisticPrimeFunction();

	@Test
	public void evaluate() {
		double result = function.evaluate(INPUT);
		assertEquals(0.24130070859, result, 0.000000001);
	}

}
