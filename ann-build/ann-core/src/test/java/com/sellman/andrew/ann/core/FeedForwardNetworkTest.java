package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertEquals;

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

public class FeedForwardNetworkTest {
	private static final Vector INPUT = new Vector(new double[] { 1, 2 });
	private static final Vector BIAS1 = new Vector(new double[] { 1000, 2000, 3000, 4000 });
	private static final Matrix WEIGHT1 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });
	private static final Vector BIAS2 = new Vector(new double[] { -10000 });
	private static final Matrix WEIGHT2 = new Matrix(new double[][] { { 11 }, { 12 }, { 13 }, { 14 } });
	private static final MatrixOperationsFactory OPERATIONS_FACTORY = new MatrixOperationsFactory();

	private TaskService taskService;
	private MatrixOperations matrixOperations;
	private FunctionGroup layer1FunctionGroup;
	private FeedForwardLayer layer1;
	private FunctionGroup layer2FunctionGroup;
	private FeedForwardLayer layer2;
	private FeedForwardNetwork network;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		matrixOperations = OPERATIONS_FACTORY.getMatrixOperations(taskService);

		layer1FunctionGroup = new FunctionGroupHelper(new ConstantAdderFunction(100), null);
		layer1 = new FeedForwardLayer("layer1", matrixOperations, WEIGHT1, BIAS1, layer1FunctionGroup);

		layer2FunctionGroup = new FunctionGroupHelper(new ConstantAdderFunction(-100), null);
		layer2 = new FeedForwardLayer("layer2", matrixOperations, WEIGHT2, BIAS2, layer2FunctionGroup);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void evaluateWith2LayerNetwork() {
		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		layers.add(layer1);
		layers.add(layer2);
		network = new FeedForwardNetwork("test", layers);

		Vector output = network.evaluate(INPUT);
		assertEquals(1, output.getColumnCount());
		assertEquals(125990.0, output.getValue(0), 0.0);
	}

	@Test
	public void evaluate1LayerNetwork() {
		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		layers.add(layer1);
		network = new FeedForwardNetwork("test", layers);

		Vector output = network.evaluate(INPUT);
		assertEquals(4, output.getColumnCount());
		assertEquals(1117.0, output.getValue(0), 0.0);
		assertEquals(2120.0, output.getValue(1), 0.0);
		assertEquals(3123.0, output.getValue(2), 0.0);
		assertEquals(4126.0, output.getValue(3), 0.0);
	}

}
