package com.sellman.andrew.vann.core.event;

abstract class RowVectorValueEvent extends DoubleValueEvent {
	private final int columnIndex;

	public RowVectorValueEvent(final Context context, final int columnIndex, final double value) {
		super(context, value);
		this.columnIndex = columnIndex;
	}

	public final int getColumnIndex() {
		return columnIndex;
	}

	public double getValue() {
		return getEventValue();
	}

	public String toString() {
		return "Row vector value is " + getValue() + " for columnIndex " + getColumnIndex() + " " + super.toString();
	}

}
