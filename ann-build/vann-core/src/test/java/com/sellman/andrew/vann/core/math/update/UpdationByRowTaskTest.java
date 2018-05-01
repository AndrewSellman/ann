package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.Matrix;

public class UpdationByRowTaskTest {
	private static final Matrix M = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
	private static final int ROW_INDEX = 1;

	private UpdationByRowTask task;
	private Matrix target;

	@Before
	public void prepareTest() {
		task = new UpdationByRowTask();
		target = new Matrix(M.getRowCount(), M.getColumnCount());
		task.setMatrixSource(M);
		task.setRowIndex(ROW_INDEX);
		task.setMatrixTarget(target);
	}

	@Test
	public void execute() {
		task.execute();

		for (int rowIndex = 0; rowIndex < M.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < M.getColumnCount(); columnIndex++) {
				if (rowIndex == ROW_INDEX) {
					assertEquals(M.getValue(rowIndex, columnIndex), target.getValue(rowIndex, columnIndex), 0.0);
				} else {
					assertEquals(0, target.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
