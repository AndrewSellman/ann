package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sellman.andrew.vann.core.math.advice.AdviceKeyC;

public class AdviceKeyCTest {

	@Test
	public void hashCodeWhenObjectsAreNotEqual() {
		AdviceKeyC key1 = new AdviceKeyC(1, 1);
		AdviceKeyC key2 = new AdviceKeyC(1, 2);
		assertTrue(key1.hashCode() != key2.hashCode());
	}

	@Test
	public void hashCodeWhenObjectsAreEqual() {
		AdviceKeyC key1 = new AdviceKeyC(1, 1);
		AdviceKeyC key2 = new AdviceKeyC(1, 1);
		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void equalsWhenObjectsAreEqual() {
		AdviceKeyC key1 = new AdviceKeyC(1, 1);
		AdviceKeyC key2 = new AdviceKeyC(1, 1);
		assertTrue(key1.equals(key2));
	}

	@Test
	public void equalsWhenObjectsAreNotEqual() {
		AdviceKeyC key1 = new AdviceKeyC(1, 1);
		AdviceKeyC key2 = new AdviceKeyC(1, 2);
		assertFalse(key1.equals(key2));
	}

}
