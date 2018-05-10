package com.sellman.andrew.vann.core.training;

import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.MathOperations;

public abstract class ErrorCalculator {
	private final MathOperations mathOps;

	public ErrorCalculator(final MathOperations mathOps) {
		this.mathOps = mathOps;
	}

	protected MathOperations getMathOperations() {
		return mathOps;
	}

	public abstract double getError(InspectableMatrix outputDifference);

}
