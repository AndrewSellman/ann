package com.sellman.andrew.vann.core;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.event.BatchErrorChangeEvent;
import com.sellman.andrew.vann.core.event.ConsoleListener;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochChangeEvent;
import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.MathOperationsFactory;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.math.add.AdditionFactory;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.vann.core.math.function.FunctionType;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.scale.ScalerFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.vann.core.math.sum.SummationFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.vann.core.math.update.UpdationFactory;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.vann.core.training.evaluator.FixedLearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumEpochErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainerExampleITCase {
	private AdditionFactory additionFactory;
	private SummationFactory summationFactory;
	private SubtractionFactory subtractionFactory;
	private ScalerFactory scalerFactory;
	private TranspositionFactory transpositionFactory;
	private HadamardMultiplicationFactory hadamardMultiplicationFactory;
	private StandardMultiplicationFactory standardMultiplicationFactory;
	private UpdationFactory updationFactory;
	private MathOperationsFactory operationsFactory;
	private TaskService highPriorityTaskService;
	private TaskService eventDispatcher;
	private MathOperations ops;
	private FeedForwardNetworkLayer layer0;
	private FeedForwardNetworkLayer layer1;
	private AbstractFeedForwardNetworkTrainer trainer;
	private FeedForwardNetwork network;
	private List<TrainingEvaluator> trainingEvaluators;
	private FeedForwardNetworkTrainerConfig config;
	private List<TrainingItem> trainingData;
	private EventManager eventManager;
	private List<Double> epochErrors;
	private LearningRateEvaluator learningRateEvaluator;
	private Cache<AdviceKey, Boolean> cache;
	private AtomicBoolean keepRoutingEvents;

	@Before
	public void prepareTest() {
		highPriorityTaskService = new TaskServiceBuilder().highPriority().build();
		cache = new CacheBuilder<AdviceKey, Boolean>("op").build();
		additionFactory = new AdditionFactory(highPriorityTaskService, new ParallelizableOperation1Advisor(cache));
		summationFactory = new SummationFactory(highPriorityTaskService, new ParallelizableOperation4Advisor(cache));
		subtractionFactory = new SubtractionFactory(highPriorityTaskService, new ParallelizableOperation1Advisor(cache));
		scalerFactory = new ScalerFactory(highPriorityTaskService, new ParallelizableOperation2Advisor(cache));
		transpositionFactory = new TranspositionFactory(highPriorityTaskService, new ParallelizableOperation3Advisor(cache));
		standardMultiplicationFactory = new StandardMultiplicationFactory(highPriorityTaskService, new ParallelizableOperation1Advisor(cache));
		hadamardMultiplicationFactory = new HadamardMultiplicationFactory(highPriorityTaskService, new ParallelizableOperation1Advisor(cache));
		updationFactory = new UpdationFactory(highPriorityTaskService, new ParallelizableOperation5Advisor(cache));
		operationsFactory = new MathOperationsFactory(additionFactory, summationFactory, subtractionFactory, scalerFactory, transpositionFactory, standardMultiplicationFactory, hadamardMultiplicationFactory, updationFactory);
		
		ops = operationsFactory.getOperations();

		eventDispatcher = new TaskServiceBuilder().lowPriority().fireAndForget().setThreadCount(1).build();
		keepRoutingEvents = new AtomicBoolean(true);
		eventManager = new EventManager(eventDispatcher, new EventListenerAdapterFactory());
		
		ConsoleListener listener = new ConsoleListener();
		eventManager.registerForDispatchedNotification(listener, EpochChangeEvent.class);
		eventManager.registerForDispatchedNotification(listener, BatchErrorChangeEvent.class);
		
		trainingEvaluators = new ArrayList<TrainingEvaluator>();
		trainingEvaluators.add(new MaximumEpochsEvaluator(100000));
		trainingEvaluators.add(new MinimumEpochErrorEvaluator(0.000001));

		learningRateEvaluator = new FixedLearningRateEvaluator(0.25);
		
		config = new FeedForwardNetworkTrainerConfig(highPriorityTaskService, trainingEvaluators, ops, eventManager, learningRateEvaluator);
		config.setBatchSize(1);

		buildTrainingAndValidationData();
	}

	@After
	public void completeTest() throws Exception {
		eventManager.close();
		highPriorityTaskService.close();
		eventDispatcher.close();
	}

	@Test
	public void train() {
		List<FeedForwardNetworkLayer> layers = new ArrayList<FeedForwardNetworkLayer>();

		Context context0 = new Context("test", 0);
		Matrix weights0 = new Matrix(new double[][] { { 0.15, 0.2 }, { 0.25, 0.3 } }, context0, eventManager);
		Vector bias0 = new Vector(new double[] { 0.35,  0.35 });
		FeedForwardNetworkLayerConfig layer0Config = new FeedForwardNetworkLayerConfig(context0, eventManager, ops, FunctionType.LOGISTIC, weights0, bias0);
		layer0 = new FeedForwardNetworkLayer(layer0Config);
		layers.add(layer0);

		Context context1 = new Context("test", 1);
		Matrix weights1 = new Matrix(new double[][] { { 0.4, 0.45 }, { 0.5, 0.55 } }, context1, eventManager);
		Vector bias1 = new Vector(new double[] { 0.6, 0.6 });
		FeedForwardNetworkLayerConfig layer1Config = new FeedForwardNetworkLayerConfig(context1, eventManager, ops, FunctionType.LOGISTIC, weights1, bias1);
		layer1 = new FeedForwardNetworkLayer(layer1Config);
		layers.add(layer1);

		FeedForwardNetworkConfig networkConfig = new FeedForwardNetworkConfig(new Context("test"), eventManager, layers);
		network = new FeedForwardNetwork(networkConfig);

		trainer = new StochasticGradientDescentFeedForwardNetworkTrainer(config, network);
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
