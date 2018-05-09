package com.sellman.andrew.vann.core.event;

public class ResetEpochErrorEvent extends EpochErrorChangeEvent {

	public ResetEpochErrorEvent(final Context context) {
		super(context, 0);
	}

}
