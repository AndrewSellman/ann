package com.sellman.andrew.ann.core.training.evaluator;

public class LearningRateRecommendation {
	private final double learningRate;
	private final boolean rollBack;
	
	public LearningRateRecommendation(double learningRate, boolean rollBack) {
		this.learningRate = learningRate;
		this.rollBack = rollBack;
	}

	public LearningRateRecommendation(double learningRate) {
		this(learningRate, false);
	}
	
	public double getLearningRate() {
		return learningRate;
	}

	public boolean shouldRollback() {
		return rollBack;
	}

}
