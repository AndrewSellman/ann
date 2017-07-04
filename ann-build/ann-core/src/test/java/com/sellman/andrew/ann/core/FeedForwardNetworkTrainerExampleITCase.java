package com.sellman.andrew.ann.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.FunctionType;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.MatrixOperationsFactory;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerExampleITCase {
	private static final MatrixOperationsFactory OPERATIONS_FACTORY = new MatrixOperationsFactory();
	private TaskService taskService;
	private MatrixOperations matrixOperations;
	private FeedForwardLayer layer1;
	private FeedForwardLayer layer2;
	private FeedForwardNetworkTrainer trainer;
	private FeedForwardNetwork network;
	private List<TrainingEvaluator> trainingEvaluators;
	private FeedForwardNetworkTrainerConfig config;
	private List<TrainingData> trainingData;
	private List<TrainingData> validationData;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		matrixOperations = OPERATIONS_FACTORY.getMatrixOperations(taskService);

		trainingEvaluators = new ArrayList<TrainingEvaluator>();
		trainingEvaluators.add(new MaximumEpochsEvaluator(10000));

		config = new FeedForwardNetworkTrainerConfig(trainingEvaluators, matrixOperations);
		config.setBatchSize(1);
		config.setLearningRate(0.5);

		buildTrainingAndValidationData();
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void train() {
		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		Matrix weights1 = new Matrix(new double[][] { {0.15, 0.2}, {0.25, 0.3} });
		Matrix bias1 = new Matrix(new double[][] { {0.35}, {0.35} });
		layer1 = new FeedForwardLayer("layer1", matrixOperations, weights1, bias1, FunctionType.LOGISTIC);
		layers.add(layer1);

		Matrix weights2 = new Matrix(new double[][] { {0.4, 0.45}, {0.5, 0.55} });
		Matrix bias2 = new Matrix(new double[][] { {0.6}, {0.6} });
		layer2 = new FeedForwardLayer("layer2", matrixOperations, weights2, bias2, FunctionType.LOGISTIC);
		layers.add(layer2);

		network = new FeedForwardNetwork("test", layers);

		trainer = new FeedForwardNetworkTrainer(config, network);
		trainer.train(trainingData);
	}

	private void buildTrainingAndValidationData() {
		trainingData = new ArrayList<TrainingData>();

		Matrix input = new Matrix(new double[][] { {0.05}, {.10}});
		Matrix expectedOutput = new Matrix(new double[][] { {0.01}, {.99}});
		TrainingData example = new TrainingData(input, expectedOutput);
		trainingData.add(example);
	}

}
