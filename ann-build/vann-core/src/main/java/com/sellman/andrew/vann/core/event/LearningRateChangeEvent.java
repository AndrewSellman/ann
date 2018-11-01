package com.sellman.andrew.vann.core.event;

public class LearningRateChangeEvent extends DoubleValueEvent {

	public LearningRateChangeEvent(final Context context, final double learningRate) {
		super(context, learningRate);
	}

	public final double getLearningRate() {
		return getEventValue();
	}

	public final String toString() {
		return "Learning rate is " + getLearningRate() + super.toString();
	}

}
