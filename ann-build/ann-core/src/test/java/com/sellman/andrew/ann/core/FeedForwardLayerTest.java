package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.ConstantAdditionFunction;
import com.sellman.andrew.ann.core.math.FunctionGroupHelper;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.MathOperationsFactory;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.math.add.AdditionFactory;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.ann.core.math.function.FunctionGroup;
import com.sellman.andrew.ann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.scale.ScalerFactory;
import com.sellman.andrew.ann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.ann.core.math.sum.SummationFactory;
import com.sellman.andrew.ann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.ann.core.math.update.UpdationFactory;

public class FeedForwardLayerTest {
	// private static final Vector BIAS = new Vector(new double[] { 1000, 2000,
	// 3000, 4000 });
	private static final Matrix INPUT = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X4 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });
	private static final Vector BIAS = new Vector(new double[] { 1000, 2000, 3000, 4000 });
	private AdditionFactory additionFactory;
	private SummationFactory summationFactory;
	private SubtractionFactory subtractionFactory;
	private ScalerFactory scalerFactory;
	private TranspositionFactory transpositionFactory;
	private HadamardMultiplicationFactory hadamardMultiplicationFactory;
	private StandardMultiplicationFactory standardMultiplicationFactory;
	private UpdationFactory updationFactory;
	private MathOperationsFactory operationsFactory;

	private TaskService taskService;
	private MathOperations ops;
	private FunctionGroup functionGroup;
	private FeedForwardNetworkLayer layer;
	
	
	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		
		additionFactory = new AdditionFactory(taskService, new ParallelizableOperation1Advisor());
		summationFactory = new SummationFactory(taskService, new ParallelizableOperation4Advisor());
		subtractionFactory = new SubtractionFactory(taskService, new ParallelizableOperation1Advisor());
		scalerFactory = new ScalerFactory(taskService, new ParallelizableOperation2Advisor());
		transpositionFactory = new TranspositionFactory(taskService, new ParallelizableOperation3Advisor());
		standardMultiplicationFactory = new StandardMultiplicationFactory(taskService, new ParallelizableOperation1Advisor());
		hadamardMultiplicationFactory = new HadamardMultiplicationFactory(taskService, new ParallelizableOperation1Advisor());
		updationFactory = new UpdationFactory(taskService, new ParallelizableOperation5Advisor());
		operationsFactory = new MathOperationsFactory(additionFactory, summationFactory, subtractionFactory, scalerFactory, transpositionFactory, standardMultiplicationFactory, hadamardMultiplicationFactory, updationFactory);
		
		functionGroup = new FunctionGroupHelper(new ConstantAdditionFunction(100), null);

		ops = operationsFactory.getOperations();
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
