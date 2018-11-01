package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class ProportionToLearningRateMomentumEvaluator extends MomentumEvaluator {
	private final double proportion;

	public ProportionToLearningRateMomentumEvaluator(final double initialMomentum, final double proportion) {
		super(initialMomentum);
		this.proportion = proportion;
	}

	@Override
	protected final double getMomentum(final TrainingProgress trainingProgress) {
		return proportion / trainingProgress.getLearningRate();
	}

}
