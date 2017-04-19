package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixAdditionTask extends AbstractTask {
	private final Matrix a;
	private final Matrix b;
	private final Matrix target;
	private final int rowIndex;

	public MatrixAdditionTask(final Matrix a, final Matrix b, final int rowIndex, final Matrix target) {
		this.a = a;
		this.b = b;
		this.target = target;
		this.rowIndex = rowIndex;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < a.getColumnCount(); columnIndex++) {
			double sum = a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, sum);
		}
	}

}
