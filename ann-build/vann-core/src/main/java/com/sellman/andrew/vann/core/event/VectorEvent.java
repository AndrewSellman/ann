package com.sellman.andrew.vann.core.event;

public abstract class VectorEvent extends Event {
	private final int rowIndex;

	public VectorEvent(final Context context, final int rowIndex) {
		super(context);
		this.rowIndex = rowIndex;
	}

	public final int getRowIndex() {
		return rowIndex;
	}

	public String toString() {
		return " for rowIndex: <" + getRowIndex() + ">" + super.toString();
	}

}
