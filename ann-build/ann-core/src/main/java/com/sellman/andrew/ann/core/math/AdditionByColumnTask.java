package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class AdditionByColumnTask extends AbstractTask {
	private final Matrix a;
	private final Matrix b;
	private final Matrix target;
	private final int columnIndex;

	public AdditionByColumnTask(CountDownLatch taskGroup, final Matrix a, final Matrix b, final int columnIndex, final Matrix target) {
		super(taskGroup);
		this.a = a;
		this.b = b;
		this.target = target;
		this.columnIndex = columnIndex;
	}

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < a.getRowCount(); rowIndex++) {
			double sum = a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, sum);
		}
	}

}
