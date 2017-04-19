package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatrixScalerTaskTest {
	private static final int ROW_INDEX = 1;
	private static final int COLUMN_INDEX = 2;
	private static final double SCALAR_VALUE = 42.0;
	private static final Matrix m2x3 = new Matrix(2, 3);

	@Test
	public void scale2by3() {
		Matrix target = new Matrix(2, 3);
		MatrixScalerTask task = new MatrixScalerTask(m2x3, ROW_INDEX, COLUMN_INDEX, (double input) -> SCALAR_VALUE, target);

		task.execute();
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 3; c++) {
				if (r == ROW_INDEX && c == COLUMN_INDEX) {
					assertEquals(SCALAR_VALUE, target.getValue(r, c), 0.0);
				} else {
					assertEquals(0.0, target.getValue(r, c), 0.0);
				}
			}
		}
	}

}
