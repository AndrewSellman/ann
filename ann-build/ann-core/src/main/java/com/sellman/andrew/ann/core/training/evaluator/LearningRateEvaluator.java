package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public abstract class LearningRateEvaluator {
	
	public abstract LearningRateRecommendation getRecommendation(final TrainingProgress progress);

}
