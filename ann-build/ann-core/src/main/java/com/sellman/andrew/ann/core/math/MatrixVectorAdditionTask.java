package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixVectorAdditionTask extends AbstractTask {
	private final Matrix a;
	private final Vector b;
	private final Matrix target;
	private final int rowIndex;

	public MatrixVectorAdditionTask(final Matrix a, final int rowIndex, final Vector b, final Matrix target) {
		this.a = a;
		this.rowIndex = rowIndex;
		this.b = b;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < a.getColumnCount(); columnIndex++) {
			double sum = a.getValue(rowIndex, columnIndex) + b.getValue(columnIndex);
			target.setValue(rowIndex, columnIndex, sum);
		}
	}

}
