package com.sellman.andrew.ann.core.math.util;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.util.RandomInitializer;

public class RandomInitializerTest {
	private static final double MININUM_VALUE = -1.0;
	private static final double MAXIMUM_VALUE = 1.0;
	private RandomInitializer initializer;
	private Matrix m;

	@Before
	public void prepareTest() {
		m = new Matrix(1000, 1000);
	}

	@Test
	public void initalizeWithout0ButMinimumValueIsGreaterThan0() {
		initializer = new RandomInitializer(.01, MAXIMUM_VALUE);

		initializer.init(m);
		assertMatrix(false, .01, MAXIMUM_VALUE);
	}

	@Test
	public void initalizeWithout0() {
		initializer = new RandomInitializer(MININUM_VALUE, MAXIMUM_VALUE);

		initializer.init(m);
		assertMatrix(false, MININUM_VALUE, MAXIMUM_VALUE);
	}

	@Test
	public void initalizeAllowing0() {
		initializer = new RandomInitializer(MININUM_VALUE, MAXIMUM_VALUE, true);

		initializer.init(m);
		assertMatrix(true, MININUM_VALUE, MAXIMUM_VALUE);
	}

	private void assertMatrix(boolean allow0, double minValue, double maxValue) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				assertTrue(m.getValue(rowIndex, columnIndex) >= minValue);
				assertTrue(m.getValue(rowIndex, columnIndex) <= maxValue);

				if (!allow0) {
					assertNotEquals(0.0, m.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
