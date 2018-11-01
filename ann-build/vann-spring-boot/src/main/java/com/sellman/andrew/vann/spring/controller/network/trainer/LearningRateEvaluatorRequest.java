package com.sellman.andrew.vann.spring.controller.network.trainer;

import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluatorType;

public class LearningRateEvaluatorRequest {
	private LearningRateEvaluatorType type;
	private double learningRate;
	private double increaseByPercent;
	private double decreaseByPercent;
	private int epochRollbackCount;
	private int consecutiveUndesirableSituationsToRollback;

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

	public int getEpochRollbackCount() {
		return epochRollbackCount;
	}

	public void setEpochRollbackCount(int epochRollbackCount) {
		this.epochRollbackCount = epochRollbackCount;
	}

	public int getConsecutiveUndesirableSituationsToRollback() {
		return consecutiveUndesirableSituationsToRollback;
	}

	public void setConsecutiveUndesirableSituationsToRollback(int consecutiveUndesirableSituationsToRollback) {
		this.consecutiveUndesirableSituationsToRollback = consecutiveUndesirableSituationsToRollback;
	}

}
