package com.sellman.andrew.vann.core.event;

public class EpochIndexChangeEvent extends IntegerValueEvent {

	public EpochIndexChangeEvent(final Context context, final int epochIndex) {
		super(context, epochIndex);
	}

	public final int getEpochIndex() {
		return getValue();
	}

	public final String toString() {
		return "Epoch index is " + getEpochIndex() + " " + super.toString();
	}

}
