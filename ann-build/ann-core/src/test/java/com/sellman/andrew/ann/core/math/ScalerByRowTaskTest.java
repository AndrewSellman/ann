package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScalerByRowTaskTest {
	private static final int ROW_INDEX = 1;
	private static final Matrix M2X3 = new Matrix(2, 3);

	private ScalerByRowTask task;

	@Before
	public void prepareTest() {
		task = new ScalerByRowTask();
		task.setMatrixA(M2X3);
		task.setFunction(new FixedValueFunction());
		task.setRowIndex(ROW_INDEX);
	}

	@Test
	public void scale2by3() {
		Matrix target = new Matrix(2, 3);
		task.setMatrixTarget(target);

		task.execute();

		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < target.getColumnCount(); columnIndex++) {
				if (rowIndex == ROW_INDEX) {
					assertEquals(FixedValueFunction.FIXED_VALUE, target.getValue(rowIndex, columnIndex), 0.0);
				} else {
					assertEquals(0.0, target.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
