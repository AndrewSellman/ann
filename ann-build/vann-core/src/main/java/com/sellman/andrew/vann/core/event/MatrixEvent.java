package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

abstract class MatrixEvent extends Event {
	private final InspectableMatrix m;

	public MatrixEvent(final Context context, final InspectableMatrix m) {
		super(context);
		this.m = m;
	}

	protected InspectableMatrix getEventMatrix() {
		return m;
	}
	
	public String toString() {
		return " matrix " + getEventMatrix().toString() + super.toString();
	}

}
