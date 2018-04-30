package com.sellman.andrew.ann.core.math.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class AbstractFunctionTest {

	private AbstractFunction f1;
	private AbstractFunction f2;
	
	@Test
	public void equalsForTheSameFunction() {
		f1 = new LogisticFunction();
		f2 = new LogisticFunction();
		assertEquals(f1, f2);
	}
	
	@Test
	public void equalsForDifferentFunction() {
		f1 = new ConstantAdditionFunction(1);
		f2 = new ConstantAdditionFunction(2);
		assertNotEquals(f1, f2);
	}
	
	@Test
	public void hasCodeForTheSameFunction() {
		f1 = new LogisticFunction();
		f2 = new LogisticFunction();
		assertEquals(f1.hashCode(), f2.hashCode());
	}
	
	@Test
	public void hashCodeForDifferentFunction() {
		f1 = new ConstantAdditionFunction(1);
		f2 = new ConstantAdditionFunction(2);
		assertNotEquals(f1.hashCode(), f2.hashCode());
	}
	
}
