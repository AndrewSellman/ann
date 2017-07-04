package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

public class MatrixSummationTask extends AbstractTask {
	private final Matrix source;
	private final int rowIndex;
	private final Vector target;

	public MatrixSummationTask(Matrix source, int rowIndex, Vector target) {
		this.source = source;
		this.rowIndex = rowIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		double result = 0;
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			result += source.getValue(rowIndex, columnIndex);
		}

		target.setValue(rowIndex, result);
	}

}
