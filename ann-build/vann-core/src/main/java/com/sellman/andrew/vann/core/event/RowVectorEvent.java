package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.RowVector;

abstract class RowVectorEvent extends Event {
	private final RowVector v;

	public RowVectorEvent(final Context context, final RowVector v) {
		super(context);
		this.v = v;
	}

	protected RowVector getEventVector() {
		return v;
	}

	public String toString() {
		return " row vector " + getEventVector().toString() + super.toString();
	}

}
