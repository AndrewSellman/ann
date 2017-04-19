package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatrixAdditionTaskTest {
	private static final int ROW_INDEX = 1;
	private static final Matrix M1 = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });
	private static final Matrix M2 = new Matrix(new double[][] { { 10, 20, 30, 40 }, { 50, 60, 70, 80 } });

	@Test
	public void add() {
		Matrix target = new Matrix(2, 4);
		MatrixAdditionTask task = new MatrixAdditionTask(M1, M2, ROW_INDEX, target);

		task.execute();
		assertEquals(0.0, target.getValue(0, 0), 0.0);
		assertEquals(0.0, target.getValue(0, 1), 0.0);
		assertEquals(0.0, target.getValue(0, 2), 0.0);
		assertEquals(0.0, target.getValue(0, 3), 0.0);
		assertEquals(55.0, target.getValue(1, 0), 0.0);
		assertEquals(66.0, target.getValue(1, 1), 0.0);
		assertEquals(77.0, target.getValue(1, 2), 0.0);
		assertEquals(88.0, target.getValue(1, 3), 0.0);
	}

}
