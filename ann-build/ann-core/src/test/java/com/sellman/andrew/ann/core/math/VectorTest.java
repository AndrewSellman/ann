package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

public class VectorTest {
	private static final int ROW_COUNT = 5;
	private double newValue;

	@Before
	public void prepareTest() {
		newValue = ThreadLocalRandom.current().nextDouble() + 1.0;
	}

	@Test
	public void constructorWithData() {
		double[] data = new double[ROW_COUNT];
		Vector v = new Vector(data);
		assertVector(v, ROW_COUNT);
	}

	@Test
	public void constructorWithDimension() {
		Vector v = new Vector(ROW_COUNT);
		assertVector(v, ROW_COUNT);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidRowReference() {
		Vector v = new Vector(ROW_COUNT);
		v.getValue(ROW_COUNT);
	}

	private void assertVector(Vector v, int expectedRowCount) {
		assertEquals(expectedRowCount, v.getRowCount());

		assertVector(v, 0.0);
		updateVector(v);
		assertVector(v, newValue);
	}

	private void assertVector(Vector v, double exepctedValue) {
		for (int rowIndex = 0; rowIndex < v.getRowCount(); rowIndex++) {
			assertEquals(exepctedValue, v.getValue(rowIndex), 0.0);
		}
	}

	private void updateVector(Vector v) {
		for (int rowIndex = 0; rowIndex < v.getRowCount(); rowIndex++) {
			v.setValue(rowIndex, newValue);
		}
	}

}
