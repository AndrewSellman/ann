package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.ColumnVector;

abstract class ColumnVectorEvent extends Event {
	private final ColumnVector v;

	public ColumnVectorEvent(final Context context, final ColumnVector v) {
		super(context);
		this.v = v;
	}

	protected ColumnVector getEventVector() {
		return v;
	}

	public String toString() {
		return " column vector " + getEventVector().toString() + super.toString();
	}

}
