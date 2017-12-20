package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class TransposeByRowTask extends AbstractTask {
	private final Matrix source;
	private final int rowIndex;
	private final Matrix target;

	public TransposeByRowTask(final CountDownLatch taskGroup, final Matrix source, final int rowIndex, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.rowIndex = rowIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			double sourceValue = source.getValue(rowIndex, columnIndex);
			target.setValue(columnIndex, rowIndex, sourceValue);
		}
	}

}
