package com.sellman.andrew.vann.core.math.function;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sellman.andrew.vann.core.math.function.FunctionType;
import com.sellman.andrew.vann.core.math.function.LogisticFunction;
import com.sellman.andrew.vann.core.math.function.LogisticPrimeFunction;

public class FunctionTypeTest {

	@Test
	public void logistic() {
		assertTrue(FunctionType.LOGISTIC.getFunction() instanceof LogisticFunction);
		assertTrue(FunctionType.LOGISTIC.getPrime() instanceof LogisticPrimeFunction);
	}
	
	
}
