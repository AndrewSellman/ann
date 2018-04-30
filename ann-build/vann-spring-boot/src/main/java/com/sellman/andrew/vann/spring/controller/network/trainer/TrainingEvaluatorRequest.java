package com.sellman.andrew.vann.spring.controller.network.trainer;

import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluatorType;

public class TrainingEvaluatorRequest {
	private TrainingEvaluatorType type;
	private int maximumEpochs;
	private double minimumError;

	public TrainingEvaluatorType getType() {
		return type;
	}

	public void setType(TrainingEvaluatorType type) {
		this.type = type;
	}

	public int getMaximumEpochs() {
		return maximumEpochs;
	}

	public void setMaximumEpochs(int maximumEpochs) {
		this.maximumEpochs = maximumEpochs;
	}

	public double getMinimumError() {
		return minimumError;
	}

	public void setMinimumError(double minimumError) {
		this.minimumError = minimumError;
	}

}
