package com.sellman.andrew.vann.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.FeedForwardNetworkLayer;
import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.MathOperationsFactory;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.add.AdditionFactory;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.vann.core.math.function.ConstantAdditionFunction;
import com.sellman.andrew.vann.core.math.function.FunctionGroup;
import com.sellman.andrew.vann.core.math.function.FunctionGroupHelper;
import com.sellman.andrew.vann.core.math.function.FunctionType;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.scale.ScalerFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.vann.core.math.sum.SummationFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.vann.core.math.update.UpdationFactory;

public class FeedForwardNetworkTest {
	private static final Matrix INPUT = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix WEIGHT1 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });
	private static final ColumnVector BIAS1 = new ColumnVector(new double[] { 1000, 2000, 3000, 4000 });
	private static final Matrix WEIGHT2 = new Matrix(new double[][] { { 11 }, { 12 }, { 13 }, { 14 } });
	private static final ColumnVector BIAS2 = new ColumnVector(new double[] { 1000});

	private AdditionFactory additionFactory;
	private SummationFactory summationFactory;
	private SubtractionFactory subtractionFactory;
	private ScalerFactory scalerFactory;
	private TranspositionFactory transpositionFactory;
	private HadamardMultiplicationFactory hadamardMultiplicationFactory;
	private StandardMultiplicationFactory standardMultiplicationFactory;
	private UpdationFactory updationFactory;
	private MathOperationsFactory operationsFactory;
	private Cache<AdviceKey, Boolean> cache;
	
	private TaskService highPriorityTaskService;
	private TaskService lowPriorityTaskService;
	private MathOperations ops;
	private FunctionGroup layer1FunctionGroup;
	private FeedForwardNetworkLayer layer1;
	private FunctionGroup layer2FunctionGroup;
	private FeedForwardNetworkLayer layer2;
	private FeedForwardNetwork network;

	@Before
	public void prepareTest() {
		highPriorityTaskService = new TaskServiceBuilder().highPriority().build();
		lowPriorityTaskService = new TaskServiceBuilder().lowPriority().fireAndForget().build();
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

		layer1FunctionGroup = new FunctionGroupHelper(new ConstantAdditionFunction(100), null);
//		layer1 = new FeedForwardLayer("layer1", matrixOperations, WEIGHT1, BIAS1, layer1FunctionGroup);

		layer2FunctionGroup = new FunctionGroupHelper(new ConstantAdditionFunction(-100), null);
//		layer2 = new FeedForwardLayer("layer2", matrixOperations, WEIGHT2, BIAS2, layer2FunctionGroup);
	}

	@After
	public void completeTest() throws Exception {
		highPriorityTaskService.close();
	}

	@Test
	public void evaluateWith2Inputs2HiddenNeurons2Outputs() {
		// List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
		// layer1 = new FeedForwardLayer("layer1", matrixOperations, new
		// Matrix(new double[][] { { 0.15, 0.25 }, { 0.2, 0.30 } }),
		// FunctionType.LOGISTIC);
		// layers.add(layer1);
		// layer2 = new FeedForwardLayer("layer2", matrixOperations, new
		// Matrix(new double[][] { { 0.4, 0.5 }, { 0.45, 0.55 } }),
		// FunctionType.LOGISTIC);
		// layers.add(layer2);
		// network = new FeedForwardNetwork("test", layers);
		//
		// Matrix output = network.evaluate(new Matrix(new double[][] { { 0.05,
		// 0.1 } }));
		// assertEquals(1, output.getRowCount());
		// assertEquals(2, output.getColumnCount());
		// assertEquals(0.75136507, output.getValue(0, 0), 0.0000001);
		// assertEquals(0.772928405, output.getValue(0, 1), 0.0000001);
	}

	@Test
	public void evaluateWith2LayerNetwork() {
//		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
//		layers.add(layer1);
//		layers.add(layer2);
//		network = new FeedForwardNetwork("test", layers);
//
//		Matrix output = network.evaluate(INPUT);
//		assertEquals(1, output.getRowCount());
//		assertEquals(1, output.getColumnCount());
//		assertEquals(5990.0, output.getValue(0, 0), 0.0);
	}

	@Test
	public void evaluate1LayerNetwork() {
//		List<FeedForwardLayer> layers = new ArrayList<FeedForwardLayer>();
//		layers.add(layer1);
//		network = new FeedForwardNetwork("test", layers);
//
//		Matrix output = network.evaluate(INPUT);
//		assertEquals(1, output.getRowCount());
//		assertEquals(4, output.getColumnCount());
//		assertEquals(1117.0, output.getValue(0, 0), 0.0);
//		assertEquals(2120.0, output.getValue(0, 1), 0.0);
//		assertEquals(3123.0, output.getValue(0, 2), 0.0);
//		assertEquals(4126.0, output.getValue(0, 3), 0.0);
	}

}
