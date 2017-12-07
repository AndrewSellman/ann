package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.event.ConsoleListener;
import com.sellman.andrew.ann.core.event.Context;
import com.sellman.andrew.ann.core.event.EpochChangeEvent;
import com.sellman.andrew.ann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.ann.core.event.EpochErrorTrackingListener;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.math.FunctionType;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.MatrixOperationsFactory;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.ann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.MinimumEpochErrorEvaluator;
import com.sellman.andrew.ann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerExampleITCase {
	private static final MatrixOperationsFactory OPERATIONS_FACTORY = new MatrixOperationsFactory();
	private TaskService highPriorityTaskService;
	private TaskService eventDispatcher;
	private MatrixOperations matrixOperations;
	private FeedForwardNetworkLayer layer0;
	private FeedForwardNetworkLayer layer1;
	private FeedForwardNetworkTrainer trainer;
	private FeedForwardNetwork network;
	private List<TrainingEvaluator> trainingEvaluators;
	private FeedForwardNetworkTrainerConfig config;
	private List<TrainingItem> trainingData;
	private EventManager eventManager;
	private List<Double> epochErrors;

	@Before
	public void prepareTest() {
		highPriorityTaskService = new TaskServiceBuilder().highPriority().build();
		matrixOperations = OPERATIONS_FACTORY.getMatrixOperations(highPriorityTaskService);

		eventDispatcher = new TaskServiceBuilder().lowPriority().fireAndForget().setThreadCount(1).build();
		eventManager = new EventManager(eventDispatcher);
		ConsoleListener<EpochChangeEvent> listener1 = new ConsoleListener<EpochChangeEvent>();
		eventManager.register(listener1, EpochChangeEvent.class);
		
		ConsoleListener<EpochErrorChangeEvent> listener2 = new ConsoleListener<EpochErrorChangeEvent>();
		eventManager.register(listener2, EpochErrorChangeEvent.class);

		epochErrors = new ArrayList<Double>();
		eventManager.register(new EpochErrorTrackingListener(epochErrors), EpochErrorChangeEvent.class);

		trainingEvaluators = new ArrayList<TrainingEvaluator>();
		trainingEvaluators.add(new MaximumEpochsEvaluator(100000));
		trainingEvaluators.add(new MinimumEpochErrorEvaluator(0.000001));

		config = new FeedForwardNetworkTrainerConfig(highPriorityTaskService, trainingEvaluators, matrixOperations, eventManager);
		config.setBatchSize(1);
		config.setLearningRate(0.5);

		buildTrainingAndValidationData();
	}

	@After
	public void completeTest() throws Exception {
		highPriorityTaskService.close();
		eventDispatcher.close();
	}

	@Test
	public void train() {
		List<FeedForwardNetworkLayer> layers = new ArrayList<FeedForwardNetworkLayer>();
		Matrix weights0 = new Matrix(new double[][] { { 0.15, 0.2 }, { 0.25, 0.3 } });
		Vector bias0 = new Vector(new double[] { 0.35,  0.35 });
		FeedForwardNetworkLayerConfig layer0Config = new FeedForwardNetworkLayerConfig(new Context("test", 0), eventManager, matrixOperations, FunctionType.LOGISTIC, weights0, bias0);
		layer0 = new FeedForwardNetworkLayer(layer0Config);
		layers.add(layer0);

		Matrix weights1 = new Matrix(new double[][] { { 0.4, 0.45 }, { 0.5, 0.55 } });
		Vector bias1 = new Vector(new double[] { 0.6, 0.6 });
		FeedForwardNetworkLayerConfig layer1Config = new FeedForwardNetworkLayerConfig(new Context("test", 0), eventManager, matrixOperations, FunctionType.LOGISTIC, weights1, bias1);
		layer1 = new FeedForwardNetworkLayer(layer1Config);
		layers.add(layer1);

		FeedForwardNetworkConfig networkConfig = new FeedForwardNetworkConfig(new Context("test"), eventManager, layers);
		network = new FeedForwardNetwork(networkConfig);

		trainer = new FeedForwardNetworkTrainer(config, network);
		trainer.train(trainingData);

		for (int e = 0; e < epochErrors.size() - 2; e++) {
			assertTrue("The error should redeuce after each epoch.", epochErrors.get(e + 1) < epochErrors.get(e));
		}
	}

	private void buildTrainingAndValidationData() {
		trainingData = new ArrayList<TrainingItem>();

		Vector input = new Vector(new double[] { 0.05, .10 });
		Vector expectedOutput = new Vector(new double[] { 0.01, .99 });
		TrainingItem example = new TrainingItem(input, expectedOutput);
		trainingData.add(example);
	}

}
