package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public class FixedLearningRateEvaluator extends LearningRateEvaluator {
	private final LearningRateRecommendation recommendation;

	public FixedLearningRateEvaluator(double fixedLearningRate) {
		recommendation = new LearningRateRecommendation(fixedLearningRate);
	}

	public LearningRateRecommendation getRecommendation(TrainingProgress progress) {
		return recommendation;
	}

}
