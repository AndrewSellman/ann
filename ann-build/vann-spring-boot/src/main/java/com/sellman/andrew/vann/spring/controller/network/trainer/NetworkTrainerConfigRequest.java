package com.sellman.andrew.vann.spring.controller.network.trainer;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sellman.andrew.vann.core.GradientDescentType;

public class NetworkTrainerConfigRequest {
	@NotNull(message = "Trainer type is required.")
	private GradientDescentType trainerType;

	@Size(min = 1, message = "At least one training evaluator is required.")
	private List<TrainingEvaluatorRequest> trainingEvaluators;

	@NotNull(message = "Learning rate evaluator is required.")
	private LearningRateEvaluatorRequest learningRateEvaluator;

	private int batchSize;
	private int savePointDepth;

	public List<TrainingEvaluatorRequest> getTrainingEvaluators() {
		return trainingEvaluators;
	}

	public void setTrainingEvaluators(List<TrainingEvaluatorRequest> trainingEvaluators) {
		this.trainingEvaluators = trainingEvaluators;
	}

	public LearningRateEvaluatorRequest getLearningRateEvaluator() {
		return learningRateEvaluator;
	}

	public void setLearningRateEvaluator(LearningRateEvaluatorRequest learningRateEvaluator) {
		this.learningRateEvaluator = learningRateEvaluator;
	}

	public GradientDescentType getTrainerType() {
		return trainerType;
	}

	public void setTrainerType(GradientDescentType trainerType) {
		this.trainerType = trainerType;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public int getSavePointDepth() {
		return savePointDepth;
	}

	public void setSavePointDepth(int savePointDepth) {
		this.savePointDepth = savePointDepth;
	}

}
