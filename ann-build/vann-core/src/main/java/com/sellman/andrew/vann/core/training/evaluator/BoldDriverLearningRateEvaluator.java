package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class BoldDriverLearningRateEvaluator extends AdapativeLearningRateEvaluator {
	private final double increaseByPercent;
	private final double decreaseByPercent;

	public BoldDriverLearningRateEvaluator(final double initalLearningRate, final double increaseByPercent, final double decreaseByPercent) {
		super(initalLearningRate);
		this.increaseByPercent = increaseByPercent;
		this.decreaseByPercent = decreaseByPercent;
	}

	@Override
	protected double getNextLearningRate(TrainingProgress progress) {
		double learningRate = getLearningRate();

		if (learningRate < 1.0) {
			learningRate = learningRate + (learningRate * increaseByPercent);
		}

		if (learningRate > 1.0) {
			learningRate = 1.0;
		}

		return learningRate;
	}

	@Override
	protected double getRollbackLearningRate(TrainingProgress progress) {
		return getLearningRate() - (getLearningRate() * decreaseByPercent);
	}
}
