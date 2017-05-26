package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixScalerTest {
	private static final double SCALAR_VALUE = 123.45;
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private MatrixScaler scaler;
	private TaskService taskService;

	private Function function = new Function() {
		@Override
		public double evaluate(double input) {
			return input + SCALAR_VALUE;
		}
	};

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().build();
		scaler = new MatrixScaler(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void scale2x3() {
		Matrix result = scaler.scale(M2X3, function);

		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 3; c++) {
				assertEquals(M2X3.getValue(r, c) + SCALAR_VALUE, result.getValue(r, c), 0.0);
			}
		}
	}

}
