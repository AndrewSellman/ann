package com.sellman.andrew.ann.core.math;

public class Scaler {
	private final double sourceRangeMin;
	private final double sourceRangeMax;
	private final double targetRangeMin;
	private final double targetRangeMax;

	public Scaler(final double sourceRangeMin, final double sourceRangeMax, final double targetRangeMin, final double targetRangeMax) {
		this.sourceRangeMin = sourceRangeMin;
		this.sourceRangeMax = sourceRangeMax;
		this.targetRangeMin = targetRangeMin;
		this.targetRangeMax = targetRangeMax;
	}

	public double scaleDown(final double value) {
		return targetRangeMin + (targetRangeMax - targetRangeMin) * (value - sourceRangeMin) / (sourceRangeMax - sourceRangeMin);
	}

	public double scaleUp(final double value) {
		return sourceRangeMin + (sourceRangeMax - sourceRangeMin) * (value - targetRangeMin) / (targetRangeMax - targetRangeMin);
	}

}
