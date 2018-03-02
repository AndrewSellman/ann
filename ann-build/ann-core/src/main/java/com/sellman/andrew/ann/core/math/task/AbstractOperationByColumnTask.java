package com.sellman.andrew.ann.core.math.task;

public abstract class AbstractOperationByColumnTask extends AbstractOperationTask {
	private int columnIndex;

	public final int getColumnIndex() {
		return columnIndex;
	}

	public final void setColumnIndex(final int columnIndex) {
		this.columnIndex = columnIndex;
	}

	@Override
	public void recycle() {
		super.recycle();
		this.columnIndex = -1;
	}

}
