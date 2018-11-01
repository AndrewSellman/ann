package com.sellman.andrew.vann.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.event.ConsoleListener;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochIndexChangeEvent;
import com.sellman.andrew.vann.core.event.EpochIndexChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.LearningRateChangeEvent;
import com.sellman.andrew.vann.core.event.LearningRateChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.MomentumChangeEvent;
import com.sellman.andrew.vann.core.event.MomentumChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeEvent;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.MathOperationsFactory;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.RowVectorFactory;
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
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.vann.core.training.FullTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.HoldOutTrainingSplitter;
import com.sellman.andrew.vann.core.training.KFoldTrainingSplitter;
import com.sellman.andrew.vann.core.training.MeanSquaredErrorCalculator;
import com.sellman.andrew.vann.core.training.Normalizer;
import com.sellman.andrew.vann.core.training.PartialTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.StochasticTrainingBatchProvider;
import com.sellman.andrew.vann.core.training.TrainingBatch;
import com.sellman.andrew.vann.core.training.TrainingBatchFactory;
import com.sellman.andrew.vann.core.training.TrainingBatchProvider;
import com.sellman.andrew.vann.core.training.TrainingExample;
import com.sellman.andrew.vann.core.training.TrainingExamplesSplitter;
import com.sellman.andrew.vann.core.training.evaluator.BoldDriverLearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.IncreasingValidationErrorRollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.LearningRateEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MaximumEpochsEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MinimumValidationErrorEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.MomentumEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.ProportionToLearningRateMomentumEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.RollbackEvaluator;
import com.sellman.andrew.vann.core.training.evaluator.TrainingEvaluator;

public class FeedForwardNetworkTrainer3By3BlockITCase {
	private static final int HIDDEN_LAYER_SIZE = 4;
	private static final int BATCH_SIZE = 50;
	private static final int SAVE_POINT_DEPTH = 4;
	private static final int TEST_DATE_SIZE = 20;
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
	private FeedForwardNetworkTrainer trainer;
	private FeedForwardNetwork network;
	private List<TrainingEvaluator> trainingEvaluators;
	private FeedForwardNetworkTrainerConfig config;
	private List<TrainingExample> trainingData;
	private List<TrainingExample> testData;
	private EventManager eventManager;
	private LearningRateEvaluator learningRateEvaluator;
	private Cache<AdviceKey, Boolean> cache;
	private TrainingBatchFactory trainingBatchFactory;
	private EventListenerAdapterFactory eventListenerAdapterFactory;
	private InspectableMatrixFactory matrixFactory;
	private RowVectorFactory rowVectorFactory;
	private MomentumEvaluator momentumEvaluator;
	private TrainingBatchProvider trainingBatchProvider;
	private TrainingBatchProvider validationBatchProvider;
	private List<RollbackEvaluator> rollbackEvaluators;
	private TrainingExamplesSplitter trainingSplitter;

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

		trainingEvaluators = new ArrayList<>();
		trainingEvaluators.add(new MaximumEpochsEvaluator(10000));
		trainingEvaluators.add(new MinimumValidationErrorEvaluator(0.099));
		
		rollbackEvaluators = new ArrayList<>();
		rollbackEvaluators.add(new IncreasingValidationErrorRollbackEvaluator(10, 2));

		eventDispatcher = new TaskServiceBuilder().lowPriority().setThreadCount(1).fireAndForget().build();

		eventListenerAdapterFactory = new EventListenerAdapterFactory();
		eventListenerAdapterFactory.register(new EpochIndexChangeListenerAdapterFactory());
		eventListenerAdapterFactory.register(new ValidationErrorChangeListenerAdapterFactory());
		eventListenerAdapterFactory.register(new NetworkLayerInputListenerAdapterFactory());
		eventListenerAdapterFactory.register(new NetworkLayerOutputListenerAdapterFactory());
		eventListenerAdapterFactory.register(new NetworkLayerBiasedWeightedInputListenerAdapterFactory());
		eventListenerAdapterFactory.register(new LearningRateChangeListenerAdapterFactory());
		eventListenerAdapterFactory.register(new MomentumChangeListenerAdapterFactory());
		eventManager = new EventManager(eventDispatcher, eventListenerAdapterFactory);

		ConsoleListener listener = new ConsoleListener();
		eventManager.registerForDispatchedNotification(listener, EpochIndexChangeEvent.class);
		eventManager.registerForDispatchedNotification(listener, ValidationErrorChangeEvent.class);
		eventManager.registerForDispatchedNotification(listener, LearningRateChangeEvent.class);
		eventManager.registerForDispatchedNotification(listener, MomentumChangeEvent.class);

		learningRateEvaluator = new BoldDriverLearningRateEvaluator(0.1, 0.25, 0.75);

		matrixFactory = new InspectableMatrixFactory();
		rowVectorFactory = new RowVectorFactory();

		trainingBatchFactory = new TrainingBatchFactory(matrixFactory);

		momentumEvaluator = new ProportionToLearningRateMomentumEvaluator(0.02, 0.0095);

		trainingBatchProvider = getTrainingBatchProvider(BATCH_SIZE);
		validationBatchProvider = new FullTrainingBatchProvider(trainingBatchFactory);
		
//		trainingSplitter = new HoldOutTrainingSplitter(0.15, true, true);
		trainingSplitter = new KFoldTrainingSplitter(10, false, true);
		
		config = new FeedForwardNetworkTrainerConfig(highPriorityTaskService, trainingEvaluators, ops, eventManager, learningRateEvaluator, new MeanSquaredErrorCalculator(ops), matrixFactory, rowVectorFactory, momentumEvaluator, trainingBatchProvider, validationBatchProvider, SAVE_POINT_DEPTH, rollbackEvaluators, trainingSplitter);
		
		buildTrainingAndTestData();
	}

	@After
	public void completeTest() throws Exception {
		highPriorityTaskService.close();
		eventManager.close();
		eventDispatcher.close();
		cache.close();
	}

	@Test
	public void train() {
		List<FeedForwardNetworkLayer> layers = new ArrayList<FeedForwardNetworkLayer>();
		FeedForwardNetworkLayerConfig layer0Config = new FeedForwardNetworkLayerConfig(new Context("test", 0), eventManager, ops, FunctionType.LOGISTIC, buildWeights(9, HIDDEN_LAYER_SIZE),
				buildBias(HIDDEN_LAYER_SIZE), matrixFactory);
		layer0 = new FeedForwardNetworkLayer(layer0Config);
		layers.add(layer0);

		FeedForwardNetworkLayerConfig layer1Config = new FeedForwardNetworkLayerConfig(new Context("test", 1), eventManager, ops, FunctionType.LOGISTIC, buildWeights(HIDDEN_LAYER_SIZE, 1),
				buildBias(1), matrixFactory);
		layer1 = new FeedForwardNetworkLayer(layer1Config);
		layers.add(layer1);

		FeedForwardNetworkConfig networkConfig = new FeedForwardNetworkConfig(new Context("test"), eventManager, layers);
		network = new FeedForwardNetwork(networkConfig);

		trainer = new FeedForwardNetworkTrainer(config, network);
		trainer.train(trainingData);

		TrainingBatch testBatch = trainingBatchFactory.createFor(testData);
		InspectableMatrix outputTest = network.evaluate(testBatch.getInput());

		for (int r = 0; r < outputTest.getRowCount(); r++) {
			for (int c = 0; c < outputTest.getColumnCount(); c++) {
				double actual = outputTest.getValue(r, c);
				double expected = testBatch.getExpectedOutput().getValue(r, c);
				System.out.println("expected = " + expected + " actual = " + actual);
			}
		}
	}

	private void buildTrainingAndTestData() {
		buildAllPossibleCases();
		Collections.shuffle(trainingData);
		testData = trainingData.stream().skip(trainingData.size() - TEST_DATE_SIZE).collect(Collectors.toList());
		trainingData = trainingData.stream().limit(trainingData.size() - TEST_DATE_SIZE).collect(Collectors.toList());

		Normalizer normalizer = new Normalizer();
		testData = normalizer.normalize(testData);
		trainingData = normalizer.normalize(trainingData);
	}

	private void buildAllPossibleCases() {
		trainingData = new ArrayList<TrainingExample>();
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
											TrainingExample data = new TrainingExample(null, null, new RowVector(input), new RowVector(expected));
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

	private RowVector buildBias(int columnCount) {
		return new RowVector(columnCount);
	}

	private TrainingBatchProvider getTrainingBatchProvider(int batchSize) {
		if (batchSize == 0) {
			return new FullTrainingBatchProvider(trainingBatchFactory);
		} else if (batchSize == 1) {
			return new StochasticTrainingBatchProvider(trainingBatchFactory);
		} else {
			return new PartialTrainingBatchProvider(trainingBatchFactory, batchSize);
		}
	}

}
