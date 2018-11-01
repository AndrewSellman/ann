package com.sellman.andrew.vann.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sellman.andrew.vann.core.GradientDescentType;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.RowVectorFactory;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.vann.core.training.FullTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.HoldOutTrainingSplitter;
import com.sellman.andrew.vann.core.training.MeanSquaredErrorCalculator;
import com.sellman.andrew.vann.core.training.PartialTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.StochasticTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.TrainingBatchFactory;
import com.sellman.andrew.vann.core.training.TrainingBatchProvider;
import com.sellman.andrew.vann.core.training.TrainingExamplesSplitter;
import com.sellman.andrew.vann.core.training.evaluator.BoldDriverLearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.FixedLearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.FixedMomentumEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.IncreasingValidationErrorRollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumEpochErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumValidationErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MomentumEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.RollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;
import com.sellman.andrew.vann.spring.config.EventBeanNames;
import com.sellman.andrew.vann.spring.config.MathBeanNames;
import com.sellman.andrew.vann.spring.config.TaskServiceBeanNames;
import com.sellman.andrew.vann.spring.config.TrainingBeanNames;
import com.sellman.andrew.vann.spring.controller.network.trainer.LearningRateEvaluatorRequest;
import com.sellman.andrew.vann.spring.controller.network.trainer.NetworkTrainerConfigRequest;
import com.sellman.andrew.vann.spring.controller.network.trainer.TrainingEvaluatorRequest;

@Service
public class FeedForwardNetworkTrainerConfigFactory {

	@Autowired
	@Qualifier(value = TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	private TaskService taskService;

	@Autowired
	@Qualifier(value = MathBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	private MathOperations mathOps;

	@Autowired
	@Qualifier(value = TrainingBeanNames.TRAINING_BATCH_FACTORY)
	private TrainingBatchFactory trainingBatchFactory;

	@Autowired
	@Qualifier(value = EventBeanNames.EVENT_MANAGER)
	private EventManager eventManager;

	@Autowired
	@Qualifier(value = MathBeanNames.INSPECTABLE_MATRIX_FACTORY)
	private InspectableMatrixFactory matrixFactory;

	@Autowired
	@Qualifier(value = MathBeanNames.ROW_VECTOR_FACTORY)
	private RowVectorFactory rowVectorFactory;

	public FeedForwardNetworkTrainerConfig create(NetworkTrainerConfigRequest request) {
		List<TrainingEvaluator> trainingEvaluators = getTrainingEvaluators(request.getTrainingEvaluators());
		LearningRateEvaluator learningRateEvaluator = getLearningRateEvaluator(request.getLearningRateEvaluator());
		MomentumEvaluator momentumEvaluator = new FixedMomentumEvaluator(.01); // TODO
		TrainingBatchProvider trainingBatchProvider = getTrainingBatchProvider(request.getTrainerType(), request.getBatchSize());
		TrainingBatchProvider validationBatchProvider = new FullTrainingBatchProvider(trainingBatchFactory);
		int savePointDepth = request.getSavePointDepth();
		List<RollbackEvaluator> rollbackEvaluators = new ArrayList<>(); // TODO
		rollbackEvaluators.add(new IncreasingValidationErrorRollbackEvaluator(10, 2));
		TrainingExamplesSplitter trainingSplitter = new HoldOutTrainingSplitter(0.15, true, true);  // TODO
		
		return new FeedForwardNetworkTrainerConfig(taskService, trainingEvaluators, mathOps, eventManager, learningRateEvaluator, new MeanSquaredErrorCalculator(mathOps), matrixFactory,
				rowVectorFactory, momentumEvaluator, trainingBatchProvider, validationBatchProvider, savePointDepth, rollbackEvaluators, trainingSplitter);
	}

	private TrainingBatchProvider getTrainingBatchProvider(GradientDescentType trainerType, int batchSize) {
		switch (trainerType) {
		case STOCHASTIC:
			return new StochasticTrainingBatchProvider(trainingBatchFactory);

		case BATCH:
			return new FullTrainingBatchProvider(trainingBatchFactory);

		case MINI_BATCH:
			return new PartialTrainingBatchProvider(trainingBatchFactory, batchSize);
		}

		throw new IllegalStateException("Unknwon or unsupported training batch provider for: " + trainerType);
	}

	private LearningRateEvaluator getLearningRateEvaluator(LearningRateEvaluatorRequest learningRateEvaluator) {
		switch (learningRateEvaluator.getType()) {
		case BOLD_DRIVER:
			return new BoldDriverLearningRateEvaluator(learningRateEvaluator.getLearningRate(), learningRateEvaluator.getIncreaseByPercent(), learningRateEvaluator.getDecreaseByPercent());

		case FIXED:
			return new FixedLearningRateEvaluator(learningRateEvaluator.getLearningRate());
		}

		throw new IllegalStateException("Unknwon or unsupported learning rate evaluator: " + learningRateEvaluator.getType());
	}

	private List<TrainingEvaluator> getTrainingEvaluators(List<TrainingEvaluatorRequest> requestedEvaluators) {
		List<TrainingEvaluator> trainingEvaluators = new ArrayList<>();
		for (TrainingEvaluatorRequest requestedEvaluator : requestedEvaluators) {
			trainingEvaluators.add(getTrainingEvaluator(requestedEvaluator));
		}
		return trainingEvaluators;
	}

	private TrainingEvaluator getTrainingEvaluator(TrainingEvaluatorRequest requestedEvaluator) {
		switch (requestedEvaluator.getType()) {
		case MAXIMUM_EPOCHS:
			return new MaximumEpochsEvaluator(requestedEvaluator.getMaximumEpochs());

		case MINIMUM_EPOCH_ERROR:
			return new MinimumEpochErrorEvaluator(requestedEvaluator.getMinimumError());

		case MINIMUM_VALIDATION_ERROR:
			return new MinimumValidationErrorEvaluator(requestedEvaluator.getMinimumError());
		}

		throw new IllegalStateException("Unknwon or unsupported training evaluator: " + requestedEvaluator.getType());
	}

}
