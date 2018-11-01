package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class AdapativeLearningRateEvaluator extends LearningRateEvaluator {

	public AdapativeLearningRateEvaluator(final double initialLearningRate) {
		super(initialLearningRate);
	}

	@Override
	protected final double getLearningRate(final TrainingProgress trainingProgress) {
		if (trainingProgress.isRollingBackEpoch()) {
			return getRollbackLearningRate(trainingProgress);
		} else {
			return getNextLearningRate(trainingProgress);
		}
	}

	protected abstract double getRollbackLearningRate(final TrainingProgress progress);

	protected abstract double getNextLearningRate(final TrainingProgress progress);

}
