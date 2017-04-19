package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixMultiplicationTask extends AbstractTask {
	private final Matrix left;
	private final Matrix right;
	private final Matrix target;
	private final int rowIndex;
	private final int columnIndex;

	public MatrixMultiplicationTask(final Matrix left, final int rowIndex, final Matrix right, final int columnIndex, final Matrix target) {
		this.left = left;
		this.right = right;
		this.target = target;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public void execute() {
		double result = 0;
		for (int i = 0; i < left.getColumnCount(); i++) {
			result += left.getValue(rowIndex, i) * right.getValue(i, columnIndex);
		}
		target.setValue(rowIndex, columnIndex, result);
	}

}
