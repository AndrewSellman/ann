package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class HadamardTask extends AbstractTask {
	private final Vector a;
	private final Vector b;
	private final int columnIndex;
	private final Vector target;

	public HadamardTask(final Vector a, final Vector b, final int columnIndex, final Vector target) {
		this.a = a;
		this.b = b;
		this.columnIndex = columnIndex;
		this.target = target;
	}

	@Override
	public void execute() {
		double result = a.getValue(columnIndex) * b.getValue(columnIndex);
		target.setValue(columnIndex, result);
	}

}
