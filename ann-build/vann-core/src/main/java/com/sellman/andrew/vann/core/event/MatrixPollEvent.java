package com.sellman.andrew.vann.core.event;

public class MatrixPollEvent extends MatrixValueEvent {

	public MatrixPollEvent(final Context context, int rowIndex, int columnIndex, double currentValue) {
		super(context, rowIndex, columnIndex, currentValue);
	}

}
