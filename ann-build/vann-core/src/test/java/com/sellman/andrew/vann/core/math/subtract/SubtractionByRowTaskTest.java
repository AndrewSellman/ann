package com.sellman.andrew.vann.core.math.subtract;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTask;

public class SubtractionByRowTaskTest {
	private static final int ROW_INDEX = 1;
	private static final Matrix M1 = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });
	private static final Matrix M2 = new Matrix(new double[][] { { 10, 20, 30, 40 }, { 50, 60, 70, 80 } });

	private SubtractionByRowTask task;

	@Before
	public void prepareTest() {
		task = new SubtractionByRowTask();
		task.setMatrixA(M1);
		task.setMatrixB(M2);
		task.setRowIndex(ROW_INDEX);
	}

	@Test
	public void subtract() {
		Matrix target = new Matrix(2, 4);
		task.setMatrixTarget(target);

		task.execute();

		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < target.getColumnCount(); columnIndex++) {
				if (rowIndex == ROW_INDEX) {
					assertEquals(M1.getValue(rowIndex, columnIndex) - M2.getValue(rowIndex, columnIndex), target.getValue(rowIndex, columnIndex), 0.0);
				} else {
					assertEquals(0.0, target.getValue(rowIndex, columnIndex), 0.0);
				}
			}
		}
	}

}
