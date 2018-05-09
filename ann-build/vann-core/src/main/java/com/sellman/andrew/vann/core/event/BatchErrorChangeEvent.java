package com.sellman.andrew.vann.core.event;

public class BatchErrorChangeEvent extends DoubleValueEvent {

	public BatchErrorChangeEvent(final Context context, final double error) {
		super(context, error);
	}

	public final double getError() {
		return getEventValue();
	}

	public final String toString() {
		return "Batch error is " + getError() + " " + super.toString();
	}

}
