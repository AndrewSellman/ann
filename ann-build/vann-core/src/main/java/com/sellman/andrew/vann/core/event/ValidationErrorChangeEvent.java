package com.sellman.andrew.vann.core.event;

public class ValidationErrorChangeEvent extends DoubleValueEvent {

	public ValidationErrorChangeEvent(final Context context, final double newError) {
		super(context, newError);
	}

	public final double getError() {
		return getEventValue();
	}

	public final String toString() {
		return "Validation error is " + getError() + super.toString();
	}

}
