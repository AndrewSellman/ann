package com.sellman.andrew.vann.core.math;

import java.util.Collections;
import java.util.List;

public class InspectableMatrixFactory {

	public InspectableMatrix createFor(RowVector v, int size) {
		List<RowVector> data = Collections.nCopies(size, v);
		return createFor(data);
	}

	public InspectableMatrix createFor(List<RowVector> data) {
		int rowCount = data.size();
		Matrix matrix = new Matrix(rowCount, data.get(0).getColumnCount());
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			double[] rowData = data.get(rowIndex).getValues();
			matrix.setRowValues(rowIndex, rowData);
		}

		return matrix;
	}

}
