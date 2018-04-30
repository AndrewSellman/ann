package com.sellman.andrew.vann.core.training;

import java.util.List;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerConfig {
	private static final double DEFAULT_PERCENT_TRAINING_DATA_FOR_VALIDATION = 0.15;
	private static final int DEFAULT_BATCH_SIZE = 1;
	private final List<TrainingEvaluator> trainingEvaluators;
	private final MathOperations mathOps;
	private final EventManager eventManager;
	private final TaskService taskService;
	private int batchSize = 1;
	private double percentTrainingDataForValidation;
	private final LearningRateEvaluator learningRateEvaluator;

	public FeedForwardNetworkTrainerConfig(final TaskService taskService, final List<TrainingEvaluator> trainingEvaluators, final MathOperations mathOperations, final EventManager eventManager, LearningRateEvaluator learningRateEvaluator) {
		this.trainingEvaluators = trainingEvaluators;
		this.mathOps = mathOperations;
		this.batchSize = DEFAULT_BATCH_SIZE;
		this.setPercentTrainingDataForValidation(DEFAULT_PERCENT_TRAINING_DATA_FOR_VALIDATION);
		this.eventManager = eventManager;
		this.taskService = taskService;
		this.learningRateEvaluator = learningRateEvaluator;
	}

	public LearningRateEvaluator getLearningRateEvaluator() {
		return learningRateEvaluator;
	}
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public MathOperations getMathOperations() {
		return mathOps;
	}

	public List<TrainingEvaluator> getTrainingEvaluators() {
		return trainingEvaluators;
	}

	public double getPercentTrainingDataForValidation() {
		return percentTrainingDataForValidation;
	}

	public void setPercentTrainingDataForValidation(double percentTrainingDataForValidation) {
		this.percentTrainingDataForValidation = percentTrainingDataForValidation;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public TaskService getTaskService() {
		return taskService;
	}

}
