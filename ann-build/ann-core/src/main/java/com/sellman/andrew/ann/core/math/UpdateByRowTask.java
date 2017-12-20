package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class UpdateByRowTask extends AbstractTask {
	private final Matrix source;
	private final Matrix target;
	private final int rowIndex;

	public UpdateByRowTask(CountDownLatch taskGroup, final Matrix source, final int rowIndex, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.target = target;
		this.rowIndex = rowIndex;
	}

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			target.setValue(rowIndex, columnIndex, source.getValue(rowIndex, columnIndex));
		}
	}

}
