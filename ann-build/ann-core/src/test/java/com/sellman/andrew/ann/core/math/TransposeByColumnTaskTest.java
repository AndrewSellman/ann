package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransposeByColumnTaskTest {
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	@Test
	public void transpose3by2Matrix() {
		Matrix transpose = new Matrix(2, 3);
		TransposeByColumnTask task = new TransposeByColumnTask(null, M3X2, 1, transpose);
		task.execute();
		assertEquals(0.0, transpose.getValue(0, 0), 0.0);
		assertEquals(0.0, transpose.getValue(0, 1), 0.0);
		assertEquals(0.0, transpose.getValue(0, 2), 0.0);
		assertEquals(8.0, transpose.getValue(1, 0), 0.0);
		assertEquals(10.0, transpose.getValue(1, 1), 0.0);
		assertEquals(12.0, transpose.getValue(1, 2), 0.0);
	}

}
