package com.sellman.andrew.ann.core.event;

public class ResetEpochErrorEvent extends Event {
	private final double originalEpochError;

	public ResetEpochErrorEvent(final Context context, final double originalEpochError) {
		super(context);
		this.originalEpochError = originalEpochError;
	}

	public String toString() {
		return "Epoch error was: <" + originalEpochError + "> and is now <0.0>" + super.toString();
	}

	public double getOriginalEpochError() {
		return originalEpochError;
	}

}
