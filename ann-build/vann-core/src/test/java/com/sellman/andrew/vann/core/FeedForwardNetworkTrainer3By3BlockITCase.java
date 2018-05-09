package com.sellman.andrew.vann.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.AbstractFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.FeedForwardNetworkConfig;
import com.sellman.andrew.vann.core.FeedForwardNetworkLayer;
import com.sellman.andrew.vann.core.FeedForwardNetworkLayerConfig;
import com.sellman.andrew.vann.core.Normalizer;
import com.sellman.andrew.vann.core.StochasticGradientDescentFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.TrainingItem;
import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.event.ConsoleListener;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochIndexChangeEvent;
import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeEvent;
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
import com.sellman.andrew.vann.core.training.evaluator.BoldDriverLearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumEpochErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumValidationErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer3By3BlockITCase {
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
	private List<TrainingItem> testData;
	private EventManager eventManager;
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

		trainingEvaluators = new ArrayList<TrainingEvaluator>();
		trainingEvaluators.add(new MaximumEpochsEvaluator(10000));
		trainingEvaluators.add(new MinimumEpochErrorEvaluator(0.0001));
		trainingEvaluators.add(new MinimumValidationErrorEvaluator(0.01));

		eventDispatcher = new TaskServiceBuilder().lowPriority().setThreadCount(1).fireAndForget().build();
		keepRoutingEvents = new AtomicBoolean(true);
		eventManager = new EventManager(eventDispatcher, new EventListenerAdapterFactory());
		ConsoleListener listener1 = new ConsoleListener();
		eventManager.registerForDispatchedNotification(listener1, Event.class);

		ConsoleListener listener2 = new ConsoleListener();
		eventManager.registerForDispatchedNotification(listener2, Event.class);

		ConsoleListener listener3 = new ConsoleListener();
		eventManager.registerForDispatchedNotification(listener3, Event.class);

		learningRateEvaluator = new BoldDriverLearningRateEvaluator(0.2, 0.05, 0.5);

		config = new FeedForwardNetworkTrainerConfig(highPriorityTaskService, trainingEvaluators, ops, eventManager, learningRateEvaluator);
		config.setBatchSize(100);

		buildTrainingAndTestData();
	}

	@After
	public void completeTest() throws Exception {
		highPriorityTaskService.close();
		eventDispatcher.close();
	}

	@Test
	public void trainStochasitc() {
		List<FeedForwardNetworkLayer> layers = new ArrayList<FeedForwardNetworkLayer>();
		FeedForwardNetworkLayerConfig layer0Config = new FeedForwardNetworkLayerConfig(new Context("test", 0), eventManager, ops, FunctionType.LOGISTIC, buildWeights(11, 9), buildBias(11));
		layer0 = new FeedForwardNetworkLayer(layer0Config);
		layers.add(layer0);

		FeedForwardNetworkLayerConfig layer1Config = new FeedForwardNetworkLayerConfig(new Context("test", 1), eventManager, ops, FunctionType.LOGISTIC, buildWeights(1, 11), buildBias(1));
		layer1 = new FeedForwardNetworkLayer(layer1Config);
		layers.add(layer1);

		FeedForwardNetworkConfig networkConfig = new FeedForwardNetworkConfig(new Context("test"), eventManager, layers);
		network = new FeedForwardNetwork(networkConfig);

		trainer = new StochasticGradientDescentFeedForwardNetworkTrainer(config, network);
		trainer.train(trainingData);

//		for (TrainingItem testItem : testData) {
//			Vector output = network.evaluate(testItem.getInput());
//			System.out.print("expected:\n" + testItem.getExpectedOutput());
//			System.out.println("actual:\n" + output);
//			assertEquals(testItem.getExpectedOutput().getValue(0), output.getValue(0), 0.01);
//		}

	}

	private void buildTrainingAndTestData() {
		buildAllPossibleCases();
		Collections.shuffle(trainingData);
		testData = trainingData.stream().skip(trainingData.size() - 10).collect(Collectors.toList());
		trainingData = trainingData.stream().limit(trainingData.size() - 10).collect(Collectors.toList());

		Normalizer normalizer = new Normalizer();
		testData = normalizer.normalize(testData);
		trainingData = normalizer.normalize(trainingData);
	}

	private void buildAllPossibleCases() {
		trainingData = new ArrayList<TrainingItem>();
		for (int x0 = 0; x0 < 2; x0++) {
			for (int x1 = 0; x1 < 2; x1++) {
				for (int x2 = 0; x2 < 2; x2++) {
					for (int x3 = 0; x3 < 2; x3++) {
						for (int x4 = 0; x4 < 2; x4++) {
							for (int x5 = 0; x5 < 2; x5++) {
								for (int x6 = 0; x6 < 2; x6++) {
									for (int x7 = 0; x7 < 2; x7++) {
										for (int x8 = 0; x8 < 2; x8++) {

											double[] input = new double[] { x0, x1, x2, x3, x4, x5, x6, x7, x8 };
											double y = 0;
											if (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 > 4) {
												y = 1;
											}

											double[] expected = new double[] { y };
											TrainingItem data = new TrainingItem(new Vector(input), new Vector(expected));
											trainingData.add(data);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private Matrix buildWeights(int rowCount, int columnCount) {
		return buildMatrix(rowCount, columnCount);
	}

	private Matrix buildMatrix(int rowCount, int columnCount) {
		Matrix weights = new Matrix(rowCount, columnCount);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				double d = 0;
				while (d == 0) {
					d = ThreadLocalRandom.current().nextDouble(-1, 1);
				}
				weights.setValue(r, c, d / Math.sqrt(columnCount));
			}
		}

		return weights;
	}

	private Vector buildBias(int rowCount) {
		return new Vector(rowCount);
	}

}
