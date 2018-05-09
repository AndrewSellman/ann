package com.sellman.andrew.vann.core.event;

public class ResetBatchErrorEvent extends BatchErrorChangeEvent {

	public ResetBatchErrorEvent(final Context context) {
		super(context, 0);
	}

}
