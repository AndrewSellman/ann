package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class UpdateByColumnTask extends AbstractTask {
	private final Matrix source;
	private final Matrix target;
	private final int columnIndex;

	public UpdateByColumnTask(CountDownLatch taskGroup, final Matrix source, final int columnIndex, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.target = target;
		this.columnIndex = columnIndex;
	}

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			target.setValue(rowIndex, columnIndex, source.getValue(rowIndex, columnIndex));
		}
	}

}
