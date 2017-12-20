package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class ScaleByRowTask extends AbstractTask {
	private final Matrix source;
	private Function function;
	private final Matrix target;
	private final int rowIndex;

	public ScaleByRowTask(final CountDownLatch taskGroup, final Matrix source, final int rowIndex, final Function function, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.function = function;
		this.target = target;
		this.rowIndex = rowIndex;
	}

	public void execute() {
		for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
			double scalar = function.evaluate(source.getValue(rowIndex, columnIndex));
			target.setValue(rowIndex, columnIndex, scalar);
		}
	}

}
