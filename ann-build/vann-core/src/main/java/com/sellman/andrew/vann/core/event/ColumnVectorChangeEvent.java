package com.sellman.andrew.vann.core.event;

public class ColumnVectorChangeEvent extends ColumnVectorValueEvent {

	public ColumnVectorChangeEvent(final Context context, int rowIndex, double newValue) {
		super(context, rowIndex, newValue);
	}
	
}
