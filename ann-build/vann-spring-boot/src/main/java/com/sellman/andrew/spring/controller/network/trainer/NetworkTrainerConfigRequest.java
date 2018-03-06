package com.sellman.andrew.spring.controller.network.trainer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.sellman.andrew.ann.core.GradientDescentType;

public class NetworkTrainerConfigRequest {
	@NotNull(message = "Trainer type is required.")
	private GradientDescentType trainerType;

	@Size(min = 1, message = "At least one training evaluator is required.")
	private List<TrainingEvaluatorRequest> trainingEvaluators;

	@NotNull(message = "Learning rate evaluator is required.")
	private LearningRateEvaluatorRequest learningRateEvaluator;

//	public NetworkTrainerConfigRequest() {
//		setTrainingEvaluators(new ArrayList<>());
//	}

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

}
