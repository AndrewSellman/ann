package com.sellman.andrew.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.evaluator.BoldDriverLearningRateEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.FixedLearningRateEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.MinimumEpochErrorEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.MinimumValidationErrorEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;
import com.sellman.andrew.spring.config.EventManagerBeanNames;
import com.sellman.andrew.spring.config.MathOperationsBeanNames;
import com.sellman.andrew.spring.config.TaskServiceBeanNames;
import com.sellman.andrew.spring.controller.network.trainer.LearningRateEvaluatorRequest;
import com.sellman.andrew.spring.controller.network.trainer.NetworkTrainerConfigRequest;
import com.sellman.andrew.spring.controller.network.trainer.TrainingEvaluatorRequest;

@Service
public class FeedForwardNetworkTrainerConfigFactory {

	@Autowired
	@Qualifier(value = TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	private TaskService taskService;

	@Autowired
	@Qualifier(value = MathOperationsBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	private MathOperations mathOps;

	@Autowired
	@Qualifier(value = EventManagerBeanNames.EVENT_MANAGER)
	private EventManager eventManager;

	public FeedForwardNetworkTrainerConfig create(NetworkTrainerConfigRequest request) {
		List<TrainingEvaluator> trainingEvaluators = getTrainingEvaluators(request.getTrainingEvaluators());
		LearningRateEvaluator learningRateEvaluator = getLearningRateEvaluator(request.getLearningRateEvaluator());
		return new FeedForwardNetworkTrainerConfig(taskService, trainingEvaluators, mathOps, eventManager, learningRateEvaluator);
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
