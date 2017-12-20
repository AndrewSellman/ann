package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class SumByColumnTask extends AbstractTask {
	private final Matrix source;
	private final int columnIndex;
	private final Vector target;

	public SumByColumnTask(CountDownLatch taskGroup, Matrix source, int columnIndex, Vector target) {
		super(taskGroup);
		this.source = source;
		this.columnIndex = columnIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		double result = 0;
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			result += source.getValue(rowIndex, columnIndex);
		}

		target.setValue(columnIndex, result);
	}

}
