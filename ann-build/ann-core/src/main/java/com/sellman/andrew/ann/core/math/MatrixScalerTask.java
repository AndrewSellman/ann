package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class MatrixScalerTask extends AbstractTask {
	private final Matrix source;
	private Function function;
	private final Matrix target;
	private final int rowIndex;
	private final int columnIndex;

	public MatrixScalerTask(final Matrix source, final int rowIndex, final int columnIndex, final Function function, final Matrix target) {
		this.source = source;
		this.function = function;
		this.target = target;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public void execute() {
		double scalar = function.evaluate(source.getValue(rowIndex, columnIndex));
		target.setValue(rowIndex, columnIndex, scalar);
	}

}
