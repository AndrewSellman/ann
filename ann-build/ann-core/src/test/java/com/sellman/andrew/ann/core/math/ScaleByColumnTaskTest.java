package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScaleByColumnTaskTest {
	private static final int COLUMN_INDEX = 1;
	private static final double FIXED_VALUE = 42.0;
	private static final Matrix M22X3 = new Matrix(2, 3);

	private Function function = new Function() {
		@Override
		public double evaluate(double input) {
			return FIXED_VALUE;
		}
	};

	@Test
	public void scale2by3() {
		Matrix target = new Matrix(2, 3);
		ScaleByColumnTask task = new ScaleByColumnTask(null, M22X3, COLUMN_INDEX, function, target);

		task.execute();
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 3; c++) {
				if (c == COLUMN_INDEX) {
					assertEquals(FIXED_VALUE, target.getValue(r, c), 0.0);
				} else {
					assertEquals(0.0, target.getValue(r, c), 0.0);
				}
			}
		}
	}

}
