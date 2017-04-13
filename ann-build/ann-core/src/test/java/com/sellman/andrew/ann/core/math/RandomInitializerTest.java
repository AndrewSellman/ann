package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.Matrix;

public class RandomInitializerTest {
	private static final double MAXIMUM_VALUE = 1.0;
	private static final double MININUM_VALUE = -1.0;
	private RandomInitializer initializer;
	
	@Before
	public void prepareTest() {
		initializer = new RandomInitializer();
	}

	@Test
	public void initalizeWithout0() {
		Matrix m = new Matrix(1000, 1000);
		initializer.init(m, MININUM_VALUE, MAXIMUM_VALUE);
		
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				assertNotEquals(0.0, m.getValue(rowIndex, columnIndex), 0.0);
				assertTrue(m.getValue(rowIndex, columnIndex) >= MININUM_VALUE);
				assertTrue(m.getValue(rowIndex, columnIndex) <= MAXIMUM_VALUE);
			}
		}
	}

}
