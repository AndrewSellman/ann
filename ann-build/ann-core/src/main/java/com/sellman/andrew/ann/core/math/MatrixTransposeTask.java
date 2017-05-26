package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixTransposeTask extends AbstractTask {
	private final Matrix source;
	private final int rowIndex;
	private final Matrix target;

	public MatrixTransposeTask(final Matrix source, final int rowIndex, final Matrix target) {
		this.source = source;
		this.rowIndex = rowIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			double sourceValue = source.getValue(rowIndex, columnIndex);
			target.setValue(columnIndex, rowIndex, sourceValue);
		}
	}

}
