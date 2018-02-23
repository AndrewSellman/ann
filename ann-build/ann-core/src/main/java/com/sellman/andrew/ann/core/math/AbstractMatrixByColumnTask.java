package com.sellman.andrew.ann.core.math;

abstract class AbstractMatrixByColumnTask extends AbstractMatrixTask {
	private int columnIndex;

	public final int getColumnIndex() {
		return columnIndex;
	}

	public final void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	@Override
	public void recycle() {
		super.recycle();
		this.columnIndex = -1;
	}

}
