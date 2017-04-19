package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.math.Vector;

public class VectorTest {
	private static final int COLUMN_COUNT = 5;
	private double newValue;

	@Before
	public void prepareTest() {
		newValue = ThreadLocalRandom.current().nextDouble() + 1.0;
	}

	@Test
	public void constructorWithData() {
		double[] data = new double[COLUMN_COUNT];
		Vector v = new Vector(data);

		assertVector(v, COLUMN_COUNT);
	}

	@Test
	public void constructorWithDimension() {
		Vector v = new Vector(COLUMN_COUNT);

		assertVector(v, COLUMN_COUNT);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidRowReference() {
		Vector v = new Vector(COLUMN_COUNT);
		v.getValue(COLUMN_COUNT);
	}

	private void assertVector(Vector v, int expectedColumnCount) {
		assertEquals(expectedColumnCount, v.getColumnCount());

		assertVector(v, 0.0);
		updateVector(v);
		assertVector(v, newValue);
	}

	private void assertVector(Vector v, double exepctedValue) {
		for (int columnIndex = 0; columnIndex < v.getColumnCount(); columnIndex++) {
			assertEquals(exepctedValue, v.getValue(columnIndex), 0.0);
		}
	}

	private void updateVector(Vector v) {
		for (int columnIndex = 0; columnIndex < v.getColumnCount(); columnIndex++) {
			v.setValue(columnIndex, newValue);
		}
	}

}
