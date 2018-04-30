package com.sellman.andrew.ann.core.math.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FunctionTypeTest {

	@Test
	public void logistic() {
		assertTrue(FunctionType.LOGISTIC.getFunction() instanceof LogisticFunction);
		assertTrue(FunctionType.LOGISTIC.getPrime() instanceof LogisticPrimeFunction);
	}
	
	
}
