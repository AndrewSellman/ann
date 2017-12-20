package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class SubtractionByColumnTask extends AbstractTask {
	private final Matrix left;
	private final Matrix right;
	private final Matrix target;
	private final int columnIndex;

	public SubtractionByColumnTask(final CountDownLatch taskGroup, final Matrix left, final Matrix right, final int columnIndex, final Matrix target) {
		super(taskGroup);
		this.left = left;
		this.right = right;
		this.target = target;
		this.columnIndex = columnIndex;
	}

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < left.getRowCount(); rowIndex++) {
			double result = left.getValue(rowIndex, columnIndex) - right.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, result);
		}
	}

}
