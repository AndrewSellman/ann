package com.sellman.andrew.ann.core.event;

public class EpochErrorChangeEvent extends Event {
	private final double originalError;
	private final double newError;

	public EpochErrorChangeEvent(final Context context, final double originalError, final double newError) {
		super(context);
		this.originalError = originalError;
		this.newError = newError;
	}

	public final double getOriginalError() {
		return originalError;
	}

	public final double getNewError() {
		return newError;
	}
	
	public final String toString() {
		return "Epoch error was: <" + originalError + "> and is now: <" + newError + ">" + super.toString();
	}

}
