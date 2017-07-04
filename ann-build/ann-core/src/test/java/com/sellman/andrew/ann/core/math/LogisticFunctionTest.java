package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogisticFunctionTest {
	private static final double INPUT = 0.3775;
	
	private Function function = new LogisticFunction();

	@Test
	public void evaluate() {
		double result = function.evaluate(INPUT);
		assertEquals(0.593269992, result, 0.000000001);
	}

}
