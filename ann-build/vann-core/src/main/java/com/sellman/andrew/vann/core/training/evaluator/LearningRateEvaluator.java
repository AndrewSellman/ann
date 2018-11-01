package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class LearningRateEvaluator {
	private double learningRate;

	public LearningRateEvaluator(final double learningRate) {
		this.learningRate = learningRate;
	}

	public final double getLearningRate() {
		return learningRate;
	}

	public final double getAdjustedLearningRate(final TrainingProgress trainingProgress) {
		learningRate = getLearningRate(trainingProgress);
		return learningRate;
	}

	protected abstract double getLearningRate(final TrainingProgress trainingProgress);

}
