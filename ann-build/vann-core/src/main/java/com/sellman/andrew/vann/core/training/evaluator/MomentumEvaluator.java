package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class MomentumEvaluator {
	private double momentum;
	
	public MomentumEvaluator(final double momentum) {
		this.momentum = momentum;
	}
	
	public final double getMomentum() {
		return momentum;
	}
	
	public final double getAdjustedMomentum(final TrainingProgress trainingProgress) {
		momentum = getMomentum(trainingProgress);
		return momentum;
	}
	
	protected abstract double getMomentum(final TrainingProgress trainingProgress);

}
