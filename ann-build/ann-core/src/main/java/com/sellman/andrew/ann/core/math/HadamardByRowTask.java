package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class HadamardByRowTask extends AbstractTask {
	private final Matrix a;
	private final Matrix b;
	private final int rowIndex;
	private final Matrix target;

	public HadamardByRowTask(CountDownLatch taskGroup, final Matrix a, final Matrix b, final int rowIndex, final Matrix target) {
		super(taskGroup);
		this.a = a;
		this.b = b;
		this.rowIndex = rowIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < target.getColumnCount(); columnIndex++) {
			double result = a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, result);
		}
	}

}
