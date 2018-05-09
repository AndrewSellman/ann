package com.sellman.andrew.vann.core.event;

public class VectorPollEvent extends VectorValueEvent {

	public VectorPollEvent(final Context context, int rowIndex, double currentValue) {
		super(context, rowIndex, currentValue);
	}

}
