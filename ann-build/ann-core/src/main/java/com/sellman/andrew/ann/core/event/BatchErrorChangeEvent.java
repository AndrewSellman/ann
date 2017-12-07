package com.sellman.andrew.ann.core.event;

public class BatchErrorChangeEvent extends Event {
	private final double originalError;
	private final double newError;

	public BatchErrorChangeEvent(final Context context, final double originalError, final double newError) {
		super(context);
		this.originalError = originalError;
		this.newError = newError;
	}

	public double getOriginalError() {
		return originalError;
	}

	public double getNewError() {
		return newError;
	}
	
	public String toString() {
		return "Batch error was: <" + originalError + ">; and is now: <" + newError + ">" + super.toString();
	}

}
