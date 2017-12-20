package com.sellman.andrew.ann.core.event;

public class MatrixChangeEvent extends MatrixEvent {
	private final double originalValue;
	private final double newValue;

	public MatrixChangeEvent(final Context context, int rowIndex, int columnIndex, double originalValue, double newValue) {
		super(context, rowIndex, columnIndex);
		this.originalValue = originalValue;
		this.newValue = newValue;
	}

	public double getOriginalValue() {
		return originalValue;
	}

	public double getNewValue() {
		return newValue;
	}

	public String toString() {
		return "Matrix value was: <" + originalValue + "> and is now <" + newValue + ">" + super.toString();
	}	
	
}
