package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public class FixedLearningRateEvaluator extends LearningRateEvaluator {
	private final LearningRateRecommendation learningRate;

	public FixedLearningRateEvaluator(double fixedLearningRate) {
		learningRate = new LearningRateRecommendation(fixedLearningRate);
	}

	public LearningRateRecommendation getRecommendation(TrainingProgress progress) {
		return learningRate;
	}

}
