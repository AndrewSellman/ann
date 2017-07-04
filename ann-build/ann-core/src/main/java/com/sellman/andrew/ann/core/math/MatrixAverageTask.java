package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixAverageTask extends AbstractTask {
	private final Matrix source;
	private final int columnIndex;
	private final Matrix target;

	public MatrixAverageTask(final Matrix source, final int columnIndex, final Matrix target) {
		this.source = source;
		this.target = target;
		this.columnIndex = columnIndex;
	}

	@Override
	public void execute() {
		double sum = 0;
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			sum = source.getValue(rowIndex, columnIndex);
		}
		target.setValue(0, columnIndex, sum / source.getRowCount());
	}

}
