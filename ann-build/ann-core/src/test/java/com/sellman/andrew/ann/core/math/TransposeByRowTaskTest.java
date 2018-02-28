package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TransposeByRowTaskTest {
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private TranspositionByRowTask task;
	private Matrix target;

	@Before
	public void prepareTest() {
		task = new TranspositionByRowTask();
		target = new Matrix(M3X2.getColumnCount(), M3X2.getRowCount());
		task.setMatrixA(M3X2);
		task.setRowIndex(1);
		task.setMatrixTarget(target);
	}

	@Test
	public void transpose3by2Matrix() {
		task.execute();
		assertEquals(0.0, target.getValue(0, 0), 0.0);
		assertEquals(9.0, target.getValue(0, 1), 0.0);
		assertEquals(0.0, target.getValue(0, 2), 0.0);
		assertEquals(0.0, target.getValue(1, 0), 0.0);
		assertEquals(10.0, target.getValue(1, 1), 0.0);
		assertEquals(0.0, target.getValue(1, 2), 0.0);
	}

}