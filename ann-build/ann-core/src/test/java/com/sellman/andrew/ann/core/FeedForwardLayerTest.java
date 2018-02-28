package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.ConstantAdditionFunction;
import com.sellman.andrew.ann.core.math.FunctionGroup;
import com.sellman.andrew.ann.core.math.FunctionGroupHelper;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.MathOperationsFactory;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardLayerTest {
	// private static final Vector BIAS = new Vector(new double[] { 1000, 2000,
	// 3000, 4000 });
	private static final Matrix INPUT = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X4 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });
	private static final Vector BIAS = new Vector(new double[] { 1000, 2000, 3000, 4000 });
	private static final MathOperationsFactory OPERATIONS_FACTORY = new MathOperationsFactory();

	private TaskService taskService;
	private MathOperations matrixOperations;
	private FunctionGroup functionGroup;
	private FeedForwardNetworkLayer layer;

	@Before
	public void prepareTest() {
		functionGroup = new FunctionGroupHelper(new ConstantAdditionFunction(100), null);
		taskService = new TaskServiceBuilder().highPriority().build();
		matrixOperations = OPERATIONS_FACTORY.getOperations(taskService);
//		layer = new FeedForwardLayer("test", matrixOperations, M2X4, BIAS, functionGroup);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void evaluate() {
//		Matrix output = layer.evaluate(INPUT);
//		assertEquals(1, output.getRowCount());
//		assertEquals(4, output.getColumnCount());
//		assertEquals(1117, output.getValue(0, 0), 0.0);
//		assertEquals(2120, output.getValue(0, 1), 0.0);
//		assertEquals(3123, output.getValue(0, 2), 0.0);
//		assertEquals(4126, output.getValue(0, 3), 0.0);
	}

}
