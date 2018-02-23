package com.sellman.andrew.ann.core.math;

abstract class AbstractMatrixByRowTask extends AbstractMatrixTask {
	private int rowIndex;

	public final int getRowIndex() {
		return rowIndex;
	}

	public final void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void recycle() {
		super.recycle();
		this.rowIndex = -1;
	}

}
