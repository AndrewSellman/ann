package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.math.Matrix;

public class MatrixTest {
	private static final int ROW_COUNT = 5;
	private static final int COLUMN_COUNT = 10;
	private double newValue;

	@Before
	public void prepareTest() {
		newValue = ThreadLocalRandom.current().nextDouble() + 1.0;
	}

	@Test
	public void constructorWithData() {
		double[][] data = new double[ROW_COUNT][COLUMN_COUNT];
		Matrix m = new Matrix(data);

		assertMatrix(m, ROW_COUNT, COLUMN_COUNT);
	}

	@Test
	public void constructorWithDimensions() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);

		assertMatrix(m, ROW_COUNT, COLUMN_COUNT);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidRowReference() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);
		m.getValue(ROW_COUNT, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidColumnReference() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);
		m.getValue(0, COLUMN_COUNT);
	}

	private void assertMatrix(Matrix m, int expectedRowCount, int expectedColumnCount) {
		assertEquals(expectedRowCount, m.getRowCount());
		assertEquals(expectedColumnCount, m.getColumnCount());

		assertMatrix(m, 0.0);
		updateMatrix(m);
		assertMatrix(m, newValue);
	}

	private void assertMatrix(Matrix m, double exepctedValue) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				assertEquals(exepctedValue, m.getValue(rowIndex, columnIndex), 0.0);
			}
		}
	}

	private void updateMatrix(Matrix m) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, newValue);
			}
		}
	}

}
