package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.Matrix;

public class UpdationByColumnTaskTest {
	private static final Matrix M = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
	private static final int COLUMN_INDEX = 1;

	private UpdationByColumnTask task;
	private Matrix target;

	@Before
	public void prepareTest() {
		task = new UpdationByColumnTask();
		target = new Matrix(M.getRowCount(), M.getColumnCount());
		task.setMatrixSource(M);
		task.setColumnIndex(COLUMN_INDEX);
		task.setMatrixTarget(target);
	}

	@Test
	public void execute() {
		task.execute();

		for (int rowIndex = 0; rowIndex < M.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < M.getColumnCount(); columnIndex++) {
				if (columnIndex == COLUMN_INDEX) {
					assertEquals(M.getValue(rowIndex, columnIndex), target.getValue(rowIndex, columnIndex), 0.0);
				} else {
					assertEquals(0, target.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
