package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class AdditionByRowTask extends AbstractTask {
	private final Matrix a;
	private final Matrix b;
	private final Matrix target;
	private final int rowIndex;

	public AdditionByRowTask(CountDownLatch taskGroup, final Matrix a, final Matrix b, final int rowIndex, final Matrix target) {
		super(taskGroup);
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
