package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class BoldDriverLearningRateEvaluator extends LearningRateEvaluator {
	private double learningRate;
	private final double increaseByPercent;
	private final double decreaseByPercent;
	
	public BoldDriverLearningRateEvaluator(double initalLearningRate, double increaseByPercent, double decreaseByPercent) {
		learningRate = initalLearningRate;
		this.increaseByPercent = increaseByPercent;
		this.decreaseByPercent = decreaseByPercent;
	}

	public LearningRateRecommendation getRecommendation(TrainingProgress progress) {
		if (progress.getEpochIndex() == 0) {
			return new LearningRateRecommendation(learningRate);
		}
		
		boolean rollback = false;
		if (progress.getEpochError() > progress.getLastEpochError()) {
			rollback = true;
			learningRate = learningRate - (learningRate * decreaseByPercent);
		} else {
			if (learningRate < 1.0) {
				learningRate = learningRate + (learningRate * increaseByPercent);	
			}
			if (learningRate > 1.0) {
				learningRate = 1.0;
			}
		}
		
		return new LearningRateRecommendation(learningRate, rollback);
	}

}
