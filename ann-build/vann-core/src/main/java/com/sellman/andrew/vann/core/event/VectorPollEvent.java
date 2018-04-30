package com.sellman.andrew.vann.core.event;

public class VectorPollEvent extends VectorEvent {
	private final double currentValue;

	public VectorPollEvent(final Context context, int rowIndex, double currentValue) {
		super(context, rowIndex);
		this.currentValue = currentValue;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public String toString() {
		return "Vector value is: <" + currentValue + "> " + super.toString();
	}

}
