package com.sellman.andrew.vann.core.math.util;

public class IntervalScale {
	private final double fromMinValue;
	private final double fromMaxValue;
	private final double toMinValue;
	private final double toMaxValue;

	public IntervalScale(final double fromMinValue, final double fromMaxValue, final double toMinValue, final double toMaxValue) {
		this.fromMinValue = fromMinValue;
		this.fromMaxValue = fromMaxValue;
		this.toMinValue = toMinValue;
		this.toMaxValue = toMaxValue;
	}

	public double down(final double d) {
		return toMinValue + (toMaxValue - toMinValue) * (d - fromMinValue) / (fromMaxValue - fromMinValue);
	}

	public double up(final double d) {
		return fromMinValue + (fromMaxValue - fromMinValue) * (d - toMinValue) / (toMaxValue - toMinValue);
	}

}
