package com.sellman.andrew.vann.core.event;

abstract class VectorValueEvent extends DoubleValueEvent {
	private final int rowIndex;

	public VectorValueEvent(final Context context, final int rowIndex, final double value) {
		super(context, value);
		this.rowIndex = rowIndex;
	}

	public final int getRowIndex() {
		return rowIndex;
	}

	public double getValue() {
		return getEventValue();
	}
	
	public String toString() {
		return "Vector value is " + getValue() + " for rowIndex " + getRowIndex() + " " + super.toString();
	}

}
