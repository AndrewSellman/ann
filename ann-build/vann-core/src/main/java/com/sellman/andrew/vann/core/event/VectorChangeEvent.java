package com.sellman.andrew.vann.core.event;

public class VectorChangeEvent extends VectorEvent {
	private final double originalValue;
	private final double newValue;

	public VectorChangeEvent(final Context context, int rowIndex, double originalValue, double newValue) {
		super(context, rowIndex);
		this.originalValue = originalValue;
		this.newValue = newValue;
	}

	public final double getOriginalValue() {
		return originalValue;
	}

	public final double getNewValue() {
		return newValue;
	}

	public final String toString() {
		return "Vector value was: <" + getOriginalValue() + "> and is now <" + getNewValue() + ">" + super.toString();
	}	
	
}
