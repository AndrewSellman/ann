package com.sellman.andrew.ann.core.event;

public abstract class MatrixEvent extends Event {
	private final int rowIndex;
	private final int columnIndex;

	public MatrixEvent(final Context context, final int rowIndex, final int columnIndex) {
		super(context);
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

}
