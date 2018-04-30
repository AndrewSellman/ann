package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class LearningRateEvaluator {
	
	public abstract LearningRateRecommendation getRecommendation(final TrainingProgress progress);

}
