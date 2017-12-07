package com.sellman.andrew.ann.core.training;

import java.util.List;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerConfig {
	private static final double DEFAULT_PERCENT_TRAINING_DATA_FOR_VALIDATION = 0.15;
	private static final int DEFAULT_BATCH_SIZE = 1;
	private static final double DEFAULT_LEARNING_RATE = 0.5;
	private final List<TrainingEvaluator> trainingEvaluators;
	private final MatrixOperations matrixOps;
	private final EventManager eventManager;
	private final TaskService taskService;
	private double learningRate = 0.5;
	private int batchSize = 1;
	private double percentTrainingDataForValidation;

	public FeedForwardNetworkTrainerConfig(final TaskService taskService, final List<TrainingEvaluator> trainingEvaluators, final MatrixOperations matrixOperations, final EventManager eventManager) {
		this.trainingEvaluators = trainingEvaluators;
		this.matrixOps = matrixOperations;
		this.learningRate = DEFAULT_LEARNING_RATE;
		this.batchSize = DEFAULT_BATCH_SIZE;
		this.setPercentTrainingDataForValidation(DEFAULT_PERCENT_TRAINING_DATA_FOR_VALIDATION);
		this.eventManager = eventManager;
		this.taskService = taskService;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public MatrixOperations getMatrixOperations() {
		return matrixOps;
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
