package com.sellman.andrew.spring.controller.network.trainer;

import com.sellman.andrew.ann.core.training.evaluator.LearningRateEvaluatorType;

public class LearningRateEvaluatorRequest {
	private LearningRateEvaluatorType type;
	private double learningRate;
	private double increaseByPercent;
	private double decreaseByPercent;

	public LearningRateEvaluatorType getType() {
		return type;
	}

	public void setType(LearningRateEvaluatorType type) {
		this.type = type;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getIncreaseByPercent() {
		return increaseByPercent;
	}

	public void setIncreaseByPercent(double increaseByPercent) {
		this.increaseByPercent = increaseByPercent;
	}

	public double getDecreaseByPercent() {
		return decreaseByPercent;
	}

	public void setDecreaseByPercent(double decreaseByPercent) {
		this.decreaseByPercent = decreaseByPercent;
	}

}
