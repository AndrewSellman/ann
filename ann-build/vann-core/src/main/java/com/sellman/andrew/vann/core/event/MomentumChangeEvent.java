package com.sellman.andrew.vann.core.event;

public class MomentumChangeEvent extends DoubleValueEvent {

	public MomentumChangeEvent(final Context context, final double momentum) {
		super(context, momentum);
	}

	public final double getMomentum() {
		return getEventValue();
	}

	public final String toString() {
		return "Momentum is " + getMomentum() + super.toString();
	}

}
