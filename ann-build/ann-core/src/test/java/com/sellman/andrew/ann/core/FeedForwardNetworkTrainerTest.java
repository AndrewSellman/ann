package com.sellman.andrew.ann.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.ConstantAdderFunction;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.FunctionGroupHelper;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixOperations;
import com.sellman.andrew.ann.core.math.MatrixOperationsFactory;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardNetworkTrainerTest {
	private static final Vector INPUT = new Vector(new double[] { 1, 2 });
	private static final Matrix WEIGHT1 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });
	private static final Vector BIAS1 = new Vector(new double[] { 10, 20, 30, 40 });
	private static final Matrix WEIGHT2 = new Matrix(new double[][] { { -1 }, { -2 }, { -3 }, { -4 } });
	private static final Vector BIAS2 = new Vector(new double[] { -2 });

	private static final MatrixOperationsFactory OPERATIONS_FACTORY = new MatrixOperationsFactory();

	private TaskService taskService;
	private MatrixOperations matrixOperations;
	private FunctionGroup layer1FunctionGroup;
	private FeedForwardLayer layer1;
	private FunctionGroup layer2FunctionGroup;
	private FeedForwardLayer layer2;
	private FeedForwardNetworkTrainer trainer;
	private FeedForwardNetwork network;

	
	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		matrixOperations = OPERATIONS_FACTORY.getMatrixOperations(taskService);

		layer1FunctionGroup = new FunctionGroupHelper(new ConstantAdderFunction(10), new ConstantAdderFunction(-10));
		layer1 = new FeedForwardLayer("layer1", matrixOperations, WEIGHT1, BIAS1, layer1FunctionGroup);
		
		layer2FunctionGroup = new FunctionGroupHelper(new ConstantAdderFunction(-10), new ConstantAdderFunction(10));
		layer2 = new FeedForwardLayer("layer2", matrixOperations, WEIGHT2, BIAS2, layer2FunctionGroup);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void train2LayerNetwork() {
		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		layers.add(layer1);
		layers.add(layer2);
		network = new FeedForwardNetwork("test", layers);
		trainer = new FeedForwardNetworkTrainer(network, matrixOperations);

		List<TrainingData> trainingData = new ArrayList<TrainingData>();
		TrainingData trainingItem = new TrainingData(INPUT, new Vector(new double[] { 1000 }));
		trainingData.add(trainingItem);
		trainer.train(trainingData);
	}

	@Test
	public void train1LayerNetwork() {
		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		layers.add(layer1);
		network = new FeedForwardNetwork("test", layers);
		trainer = new FeedForwardNetworkTrainer(network, matrixOperations);

		List<TrainingData> trainingData = new ArrayList<TrainingData>();
		TrainingData trainingItem = new TrainingData(INPUT, new Vector(new double[] { 1000, 2000, 3000, 4000 }));
		trainingData.add(trainingItem);
		trainer.train(trainingData);
	}

}
