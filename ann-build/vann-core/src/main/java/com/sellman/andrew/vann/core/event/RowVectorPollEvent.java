package com.sellman.andrew.vann.core.event;

public class RowVectorPollEvent extends RowVectorValueEvent {

	public RowVectorPollEvent(final Context context, int rowIndex, double currentValue) {
		super(context, rowIndex, currentValue);
	}

}
