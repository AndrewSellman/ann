package com.sellman.andrew.vann.core.event;

abstract class IntegerValueEvent extends Event {
	private final int value;

	public IntegerValueEvent(final Context context, final int value) {
		super(context);
		this.value = value;
	}

	protected int getValue() {
		return value;
	}

}
