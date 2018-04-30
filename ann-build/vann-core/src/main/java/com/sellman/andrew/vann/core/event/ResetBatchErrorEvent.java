package com.sellman.andrew.vann.core.event;

public class ResetBatchErrorEvent extends Event {
	private final double originalBatchError;

	public ResetBatchErrorEvent(final Context context, final double originalBatchError) {
		super(context);
		this.originalBatchError = originalBatchError;
	}

	public String toString() {
		return "Batch error was: <" + originalBatchError + "> and is now <0.0>" + super.toString();
	}

	public double getOriginalBatchError() {
		return originalBatchError;
	}

}
