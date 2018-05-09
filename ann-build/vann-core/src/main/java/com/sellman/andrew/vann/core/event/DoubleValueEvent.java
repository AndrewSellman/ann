package com.sellman.andrew.vann.core.event;

abstract class DoubleValueEvent extends Event {
	private final double value;

	public DoubleValueEvent(final Context context, final double value) {
		super(context);
		this.value = value;
	}

	protected double getEventValue() {
		return value;
	}

}
