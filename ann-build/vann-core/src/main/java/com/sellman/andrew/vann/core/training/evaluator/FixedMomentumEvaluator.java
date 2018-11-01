package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class FixedMomentumEvaluator extends MomentumEvaluator {

	public FixedMomentumEvaluator(double fixedMomentum) {
		super(fixedMomentum);
	}

	@Override
	protected final double getMomentum(final TrainingProgress trainingProgress) {
		return getMomentum();
	}

}
