package com.sellman.andrew.ann.core.event;

public abstract class MatrixEvent extends Event {
	private final int rowIndex;
	private final int columnIndex;

	public MatrixEvent(final Context context, final int rowIndex, final int columnIndex) {
		super(context);
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public final int getRowIndex() {
		return rowIndex;
	}

	public final int getColumnIndex() {
		return columnIndex;
	}

	public String toString() {
		return " for rowIndex: <" + getRowIndex() + "> and columnIndex: <" + getColumnIndex() + ">" + super.toString();
	}

}
