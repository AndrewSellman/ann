package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sellman.andrew.ann.core.Matrix;

public class MultiplicationTaskTest {
	private static final Matrix m2x3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix m3x2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	@Test
	public void multiplicationStep3by2with2by3() {
		Matrix target = new Matrix(3, 3);
		MultiplicationTask task = new MultiplicationTask(m3x2, 1, m2x3, 2, target);

		task.execute();
		assertEquals(87.0, target.getValue(1, 2), 0.0);
	}

}
