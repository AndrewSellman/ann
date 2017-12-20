package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class ScaleByColumnTask extends AbstractTask {
	private final Matrix source;
	private Function function;
	private final Matrix target;
	private final int columnIndex;

	public ScaleByColumnTask(final CountDownLatch taskGroup, final Matrix source, final int columnIndex, final Function function, final Matrix target) {
		super(taskGroup);
		this.source = source;
		this.function = function;
		this.target = target;
		this.columnIndex = columnIndex;
	}

	public void execute() {
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			double scalar = function.evaluate(source.getValue(rowIndex, columnIndex));
			target.setValue(rowIndex, columnIndex, scalar);
		}
	}

}
