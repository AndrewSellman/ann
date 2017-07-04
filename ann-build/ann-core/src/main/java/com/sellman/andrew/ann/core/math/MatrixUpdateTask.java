package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixUpdateTask extends AbstractTask {
	private final Matrix source;
	private final Matrix target;
	private final int rowIndex;

	public MatrixUpdateTask(final Matrix source, final Matrix target, final int rowIndex) {
		this.source = source;
		this.target = target;
		this.rowIndex = rowIndex;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			target.setValue(rowIndex, columnIndex, source.getValue(rowIndex, columnIndex));
		}
	}

}
