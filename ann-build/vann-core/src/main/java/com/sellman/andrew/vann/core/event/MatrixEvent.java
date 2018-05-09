package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

abstract class MatrixEvent extends Event {
	private final Matrix m;

	public MatrixEvent(final Context context, final Matrix m) {
		super(context);
		this.m = m;
	}

	protected Matrix getEventMatrix() {
		return m;
	}
	
	public String toString() {
		return " matrix " + getEventMatrix().toString() + super.toString();
	}

}
