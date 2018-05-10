package com.sellman.andrew.vann.core.event;

public class RowVectorChangeEvent extends RowVectorValueEvent {

	public RowVectorChangeEvent(final Context context, int columnIndex, double newValue) {
		super(context, columnIndex, newValue);
	}
	
}
