package com.sellman.andrew.vann.core.event;

public class MatrixChangeEvent extends MatrixValueEvent {

	public MatrixChangeEvent(final Context context, int rowIndex, int columnIndex, double newValue) {
		super(context, rowIndex, columnIndex, newValue);
	}
	
}
