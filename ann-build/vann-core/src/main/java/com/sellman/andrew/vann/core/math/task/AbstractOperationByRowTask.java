package com.sellman.andrew.vann.core.math.task;

public abstract class AbstractOperationByRowTask extends AbstractOperationTask {
	private int rowIndex;

	public final int getRowIndex() {
		return rowIndex;
	}

	public final void setRowIndex(final int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void recycle() {
		super.recycle();
		this.rowIndex = -1;
	}

}
