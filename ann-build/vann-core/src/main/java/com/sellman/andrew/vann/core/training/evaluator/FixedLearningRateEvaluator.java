package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class FixedLearningRateEvaluator extends LearningRateEvaluator {

	public FixedLearningRateEvaluator(double fixedLearningRate) {
		super(fixedLearningRate);
	}

	@Override
	protected double getLearningRate(TrainingProgress trainingProgress) {
		return getLearningRate();
	}

}
