package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Vector;

abstract class VectorEvent extends Event {
	private final Vector v;

	public VectorEvent(final Context context, final Vector v) {
		super(context);
		this.v = v;
	}

	protected Vector getEventVector() {
		return v;
	}

	public String toString() {
		return " vector " + getEventVector().toString() + super.toString();
	}

}
