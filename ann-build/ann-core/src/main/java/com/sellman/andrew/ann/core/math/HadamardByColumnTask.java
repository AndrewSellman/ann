package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class HadamardByColumnTask extends AbstractTask {
	private final Matrix a;
	private final Matrix b;
	private final int columnIndex;
	private final Matrix target;

	public HadamardByColumnTask(CountDownLatch taskGroup, final Matrix a, final Matrix b, final int columnIndex, final Matrix target) {
		super(taskGroup);
		this.a = a;
		this.b = b;
		this.columnIndex = columnIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			double result = a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, result);
		}
	}

}
