package com.sellman.andrew.ann.core.math.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sellman.andrew.ann.core.math.function.FixedValueFunction;
import com.sellman.andrew.ann.core.math.function.FunctionType;

public class AdviceKeyBTest {

	@Test
	public void hashCodeWhenObjectsAreNotEqual() {
		AdviceKeyB key1 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		AdviceKeyB key2 = new AdviceKeyB(1, 2, FunctionType.LOGISTIC.getFunction());
		assertTrue(key1.hashCode() != key2.hashCode());

		key2 = new AdviceKeyB(1, 1, new FixedValueFunction());
		assertTrue(key1.hashCode() != key2.hashCode());
	}

	@Test
	public void hashCodeWhenObjectsAreEqual() {
		AdviceKeyB key1 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		AdviceKeyB key2 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void equalsWhenObjectsAreEqual() {
		AdviceKeyB key1 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		AdviceKeyB key2 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		assertTrue(key1.equals(key2));
	}

	@Test
	public void equalsWhenObjectsAreNotEqual() {
		AdviceKeyB key1 = new AdviceKeyB(1, 1, FunctionType.LOGISTIC.getFunction());
		AdviceKeyB key2 = new AdviceKeyB(1, 2, FunctionType.LOGISTIC.getFunction());
		assertFalse(key1.equals(key2));

		key2 = new AdviceKeyB(1, 1, new FixedValueFunction());
		assertFalse(key1.equals(key2));
	}

}
