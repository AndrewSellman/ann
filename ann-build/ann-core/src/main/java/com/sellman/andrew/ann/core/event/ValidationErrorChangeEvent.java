package com.sellman.andrew.ann.core.event;

public class ValidationErrorChangeEvent extends Event {
	private final double originalError;
	private final double newError;

	public ValidationErrorChangeEvent(final Context context, final double originalError, final double newError) {
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
		return "Validation error was: <" + originalError + "> and is now: <" + newError + ">" + super.toString();
	}

}
