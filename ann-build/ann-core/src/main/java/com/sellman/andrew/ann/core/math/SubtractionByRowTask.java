package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class SubtractionByRowTask extends AbstractTask {
	private final Matrix left;
	private final Matrix right;
	private final Matrix target;
	private final int rowIndex;

	public SubtractionByRowTask(final CountDownLatch taskGroup, final Matrix left, final Matrix right, final int rowIndex, final Matrix target) {
		super(taskGroup);
		this.left = left;
		this.right = right;
		this.target = target;
		this.rowIndex = rowIndex;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < left.getColumnCount(); columnIndex++) {
			double result = left.getValue(rowIndex, columnIndex) - right.getValue(rowIndex, columnIndex);
			target.setValue(rowIndex, columnIndex, result);
		}
	}

}
