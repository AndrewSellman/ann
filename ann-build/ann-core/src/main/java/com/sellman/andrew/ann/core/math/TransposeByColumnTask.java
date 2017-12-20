package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class TransposeByColumnTask extends AbstractTask {
	private final Matrix source;
	private final int columnIndex;
	private final Matrix target;

	public TransposeByColumnTask(final CountDownLatch taskGroup, final Matrix source, final int columnIndex, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.columnIndex = columnIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			double sourceValue = source.getValue(rowIndex, columnIndex);
			target.setValue(columnIndex, rowIndex, sourceValue);
		}
	}

}
