package com.sellman.andrew.vann.core.training;

import java.util.List;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.RowVectorFactory;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MomentumEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.RollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerConfig {
	private static final double DEFAULT_PERCENT_TRAINING_DATA_FOR_VALIDATION = 0.15;
	private final List<TrainingEvaluator> trainingEvaluators;
	private final MathOperations mathOps;
	private final EventManager eventManager;
	private final TaskService taskService;
	private final LearningRateEvaluator learningRateEvaluator;
	private final ErrorCalculator errorCalculator;
	private final InspectableMatrixFactory matrixFactory;
	private final RowVectorFactory rowVectorFactory;
	private final MomentumEvaluator momentumEvaluator;
	private final TrainingBatchProvider trainingBatchProvider;
	private final TrainingBatchProvider validationBatchProvider;
	private final int savePointDepth;
	private final List<RollbackEvaluator> rollbackEvaluators;
	private final TrainingExamplesSplitter trainingSplitter;

	public FeedForwardNetworkTrainerConfig(final TaskService taskService, final List<TrainingEvaluator> trainingEvaluators, final MathOperations mathOperations,
			final EventManager eventManager, final LearningRateEvaluator learningRateEvaluator, final ErrorCalculator errorCalculator, final InspectableMatrixFactory matrixFactory,
			final RowVectorFactory rowVectorFactory, final MomentumEvaluator momentumEvaluator, final TrainingBatchProvider trainingBatchProvider,
			final TrainingBatchProvider validationBatchProvider, final int maximumSavePointDepth, final List<RollbackEvaluator> rollbackEvaluators, final TrainingExamplesSplitter trainingSplitter) {
		this.trainingEvaluators = trainingEvaluators;
		this.mathOps = mathOperations;
		this.eventManager = eventManager;
		this.taskService = taskService;
		this.learningRateEvaluator = learningRateEvaluator;
		this.errorCalculator = errorCalculator;
		this.matrixFactory = matrixFactory;
		this.rowVectorFactory = rowVectorFactory;
		this.momentumEvaluator = momentumEvaluator;
		this.trainingBatchProvider = trainingBatchProvider;
		this.validationBatchProvider = validationBatchProvider;
		this.savePointDepth = maximumSavePointDepth;
		this.rollbackEvaluators = rollbackEvaluators;
		this.trainingSplitter = trainingSplitter;
	}

	public TrainingBatchProvider getTrainingBatchProvider() {
		return trainingBatchProvider;
	}

	public TrainingBatchProvider getValidationBatchProvider() {
		return validationBatchProvider;
	}

	public LearningRateEvaluator getLearningRateEvaluator() {
		return learningRateEvaluator;
	}

	public RowVectorFactory getRowVectorFactory() {
		return rowVectorFactory;
	}

	public ErrorCalculator getErrorCalculator() {
		return errorCalculator;
	}

	public InspectableMatrixFactory getMatrixFactory() {
		return matrixFactory;
	}

	public MathOperations getMathOperations() {
		return mathOps;
	}

	public List<TrainingEvaluator> getTrainingEvaluators() {
		return trainingEvaluators;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public MomentumEvaluator getMomentumEvaluator() {
		return momentumEvaluator;
	}

	public int getSavePointDepth() {
		return savePointDepth;
	}

	public List<RollbackEvaluator> getRollbackEvaluators() {
		return rollbackEvaluators;
	}

	public TrainingExamplesSplitter getTrainingSplitter() {
		return trainingSplitter;
	}

}
