package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.MatrixAdder;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.MatrixMultiplier;
import com.sellman.andrew.ann.core.math.MatrixScaler;
import com.sellman.andrew.ann.core.math.Vector;

public class FeedForwardLayerTest {
	private static final Vector BIAS = new Vector(new double[] { 1000, 2000, 3000, 4000 });
	private static final Vector V2 = new Vector(new double[] { 1, 2 });
	private static final Matrix M2X4 = new Matrix(new double[][] { { 3, 4, 5, 6 }, { 7, 8, 9, 10 } });

	private FeedForwardLayer layer;
	private MatrixMultiplier multiplier;
	private MatrixAdder adder;
	private MatrixScaler scaler;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		multiplier = new MatrixMultiplier(taskService);
		scaler = new MatrixScaler(taskService);
		adder = new MatrixAdder(taskService);
		layer = new FeedForwardLayer("test", multiplier, adder, scaler, M2X4, BIAS, (double input) -> input + 100);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void evaluate() {
		Vector output = layer.evaluate(V2);
		assertEquals(4, output.getColumnCount());
		assertEquals(1117, output.getValue(0), 0.0);
		assertEquals(2120, output.getValue(1), 0.0);
		assertEquals(3123, output.getValue(2), 0.0);
		assertEquals(4126, output.getValue(3), 0.0);
	}

}
