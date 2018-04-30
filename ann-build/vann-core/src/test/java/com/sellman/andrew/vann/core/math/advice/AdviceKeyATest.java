package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sellman.andrew.vann.core.math.advice.AdviceKeyA;

public class AdviceKeyATest {

	@Test
	public void hashCodeWhenObjectsAreNotEqual() {
		AdviceKeyA key1 = new AdviceKeyA(1, 1, 2, 2);
		AdviceKeyA key2 = new AdviceKeyA(1, 1, 2, 3);
		assertTrue(key1.hashCode() != key2.hashCode());
	}

	@Test
	public void hashCodeWhenObjectsAreEqual() {
		AdviceKeyA key1 = new AdviceKeyA(1, 1, 2, 2);
		AdviceKeyA key2 = new AdviceKeyA(1, 1, 2, 2);
		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void equalsWhenObjectsAreEqual() {
		AdviceKeyA key1 = new AdviceKeyA(1, 1, 2, 2);
		AdviceKeyA key2 = new AdviceKeyA(1, 1, 2, 2);
		assertTrue(key1.equals(key2));
	}

	@Test
	public void equalsWhenObjectsAreNotEqual() {
		AdviceKeyA key1 = new AdviceKeyA(1, 1, 2, 2);
		AdviceKeyA key2 = new AdviceKeyA(1, 1, 2, 3);
		assertFalse(key1.equals(key2));
	}

}
