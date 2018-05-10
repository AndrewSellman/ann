package com.sellman.andrew.vann.core.event;

public class ColumnVectorPollEvent extends ColumnVectorValueEvent {

	public ColumnVectorPollEvent(final Context context, int rowIndex, double currentValue) {
		super(context, rowIndex, currentValue);
	}

}
