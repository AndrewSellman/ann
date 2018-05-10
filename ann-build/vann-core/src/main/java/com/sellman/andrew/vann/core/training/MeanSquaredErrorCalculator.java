package com.sellman.andrew.vann.core.training;

import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.MathOperations;

public class MeanSquaredErrorCalculator extends ErrorCalculator {

	public MeanSquaredErrorCalculator(MathOperations mathOps) {
		super(mathOps);
	}

	@Override
	public double getError(final InspectableMatrix outputDifference) {
		InspectableMatrix outputError = getMathOperations().scale(outputDifference, x -> 1.0 / outputDifference.getRowCount() * Math.pow(x, 2));
		return getMathOperations().sum(outputError);
	}

}
