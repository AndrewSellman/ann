package com.sellman.andrew.vann.core.event;

public class MatrixPollEvent extends MatrixEvent {
	private final double currentValue;

	public MatrixPollEvent(final Context context, int rowIndex, int columnIndex, double currentValue) {
		super(context, rowIndex, columnIndex);
		this.currentValue = currentValue;
	}

	public final double getCurrentValue() {
		return currentValue;
	}

	public final String toString() {
		return "Matrix value is: <" + currentValue + "> " + super.toString();
	}

}
