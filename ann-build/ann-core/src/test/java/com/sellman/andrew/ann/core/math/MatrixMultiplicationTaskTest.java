package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatrixMultiplicationTaskTest {
	private static final int ROW_INDEX = 1;
	private static final int COLUMN_INDEX = 2;
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	@Test
	public void multiplicationStep3by2with2by3() {
		Matrix target = new Matrix(3, 3);
		MatrixMultiplicationTask task = new MatrixMultiplicationTask(M3X2, ROW_INDEX, M2X3, COLUMN_INDEX, target);

		task.execute();
		assertEquals(87.0, target.getValue(ROW_INDEX, COLUMN_INDEX), 0.0);
	}

}
