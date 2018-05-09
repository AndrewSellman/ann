package com.sellman.andrew.vann.core.event;

public class EpochErrorChangeEvent extends DoubleValueEvent {

	public EpochErrorChangeEvent(final Context context, final double error) {
		super(context, error);
	}

	public final double getError() {
		return getEventValue();
	}

	public final String toString() {
		return "Epoch error is " + getError() + " " + super.toString();
	}

}
