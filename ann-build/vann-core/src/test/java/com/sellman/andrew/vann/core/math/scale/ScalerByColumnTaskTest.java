package com.sellman.andrew.vann.core.math.scale;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.function.FixedValueFunction;
import com.sellman.andrew.vann.core.math.scale.ScalerByColumnTask;

public class ScalerByColumnTaskTest {
	private static final int COLUMN_INDEX = 1;
	private static final Matrix M2X3 = new Matrix(2, 3);

	private ScalerByColumnTask task;

	@Before
	public void prepareTest() {
		task = new ScalerByColumnTask();
		task.setMatrixA(M2X3);
		task.setFunction(new FixedValueFunction());
		task.setColumnIndex(COLUMN_INDEX);
	}

	@Test
	public void scale2by3Matrix() {
		Matrix target = new Matrix(2, 3);
		task.setMatrixTarget(target);

		task.execute();

		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < target.getColumnCount(); columnIndex++) {
				if (columnIndex == COLUMN_INDEX) {
					assertEquals(FixedValueFunction.FIXED_VALUE, target.getValue(rowIndex, columnIndex), 0.0);
				} else {
					assertEquals(0.0, target.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
