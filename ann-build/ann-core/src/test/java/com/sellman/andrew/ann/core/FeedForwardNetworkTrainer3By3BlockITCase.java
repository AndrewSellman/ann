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

public class FeedForwardNetworkTrainer3By3BlockITCase {
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
		config.setLearningRate(1);

		buildTrainingAndValidationData();
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void train() {

		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		layer1 = new FeedForwardLayer("layer1", matrixOperations, buildWeights(11, 9), buildBias(11), FunctionType.LOGISTIC);
		layers.add(layer1);
		layer2 = new FeedForwardLayer("layer2", matrixOperations, buildWeights(1, 11), buildBias(1), FunctionType.LOGISTIC);

		layers.add(layer2);
		network = new FeedForwardNetwork("test", layers);

		trainer = new FeedForwardNetworkTrainer(config, network);
		trainer.train(trainingData);
	}

	private void buildTrainingAndValidationData() {
		buildAllPossibleCases();
		Collections.shuffle(trainingData);
		validationData = trainingData.stream().skip(trainingData.size() - 10).collect(Collectors.toList());
		trainingData = trainingData.stream().limit(trainingData.size() - 10).collect(Collectors.toList());
	}

	private void buildAllPossibleCases() {
		trainingData = new ArrayList<TrainingData>();
		for (int x0 = 0; x0 < 2; x0++) {
			for (int x1 = 0; x1 < 2; x1++) {
				for (int x2 = 0; x2 < 2; x2++) {
					for (int x3 = 0; x3 < 2; x3++) {
						for (int x4 = 0; x4 < 2; x4++) {
							for (int x5 = 0; x5 < 2; x5++) {
								for (int x6 = 0; x6 < 2; x6++) {
									for (int x7 = 0; x7 < 2; x7++) {
										for (int x8 = 0; x8 < 2; x8++) {

											double[][] input = new double[][] { {x0}, {x1}, {x2}, {x3}, {x4}, {x5}, {x6}, {x7}, {x8} };
											double y = 0;
											if (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 > 4) {
												y = 1;
											}
											
											double[][] expected = new double[][] { {y} };
											TrainingData data = new TrainingData(new Matrix(input), new Matrix(expected));
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
		double min = 0;
		double max = 0.01;
		Matrix weights = new Matrix(rowCount, columnCount);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				double d = 0;
				while (d == 0) {
					d= ThreadLocalRandom.current().nextDouble(min, max);
				}
				weights.setValue(r, c, d);
			}
		}
		
		return weights;
	}

	private Matrix buildBias(int rowCount) {
		return new Matrix(rowCount, 1); 
	}

}
