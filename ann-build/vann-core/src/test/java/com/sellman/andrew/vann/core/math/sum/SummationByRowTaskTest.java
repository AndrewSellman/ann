package com.sellman.andrew.vann.core.math.sum;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.sum.SummationByRowTask;

public class SummationByRowTaskTest {
	private static final int ROW_INDEX = 1;
	private static final Matrix M = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });

	private SummationByRowTask task;

	@Before
	public void prepareTest() {
		task = new SummationByRowTask();
		task.setMatrixA(M);
		task.setRowIndex(ROW_INDEX);
	}

	@Test
	public void sum() {
		ColumnVector target = new ColumnVector(2);
		task.setVectorTarget(target);

		task.execute();

		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			if (rowIndex == ROW_INDEX) {
				assertEquals(26.0, target.getValue(rowIndex), 0.0);
			} else {
				assertEquals(0.0, target.getValue(rowIndex), 0.0);
			}
		}
	}

}
