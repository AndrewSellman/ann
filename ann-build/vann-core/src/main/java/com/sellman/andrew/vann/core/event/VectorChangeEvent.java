package com.sellman.andrew.vann.core.event;

public class VectorChangeEvent extends VectorValueEvent {

	public VectorChangeEvent(final Context context, int rowIndex, double newValue) {
		super(context, rowIndex, newValue);
	}
	
}
