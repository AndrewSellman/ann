package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class SumByRowTask extends AbstractTask {
	private final Matrix source;
	private final int rowIndex;
	private final Vector target;

	public SumByRowTask(CountDownLatch taskGroup, Matrix source, int rowIndex, Vector target) {
		super(taskGroup);
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
