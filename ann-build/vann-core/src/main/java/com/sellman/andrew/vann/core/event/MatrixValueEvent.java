package com.sellman.andrew.vann.core.event;

abstract class MatrixValueEvent extends DoubleValueEvent {
	private final int rowIndex;
	private final int columnIndex;

	public MatrixValueEvent(final Context context, final int rowIndex, final int columnIndex, double value) {
		super(context, value);
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public final int getRowIndex() {
		return rowIndex;
	}

	public final int getColumnIndex() {
		return columnIndex;
	}

	public double getValue() {
		return getEventValue();
	}
	
	public String toString() {
		return "Matrix value is " + getValue() + " for rowIndex " + getRowIndex() + " and columnIndex " + getColumnIndex() + " " + super.toString();
	}

}
