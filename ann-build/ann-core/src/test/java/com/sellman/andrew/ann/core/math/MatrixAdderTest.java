package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixAdderTest {
	private static final Matrix M1 = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });
	private static final Matrix M2 = new Matrix(new double[][] { { 10, 20, 30, 40 }, { 50, 60, 70, 80 } });

	private Adder adder;
	private TaskService taskService;
	private ParallelizableOperationAdvisor advisor;
	
	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		advisor = mock(ParallelizableOperationAdvisor.class);
		adder = new Adder(taskService, advisor);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void addMatrices() {
		Matrix result = adder.add(M1, M2);
		assertEquals(11.0, result.getValue(0, 0), 0.0);
		assertEquals(22.0, result.getValue(0, 1), 0.0);
		assertEquals(33.0, result.getValue(0, 2), 0.0);
		assertEquals(44.0, result.getValue(0, 3), 0.0);
		assertEquals(55.0, result.getValue(1, 0), 0.0);
		assertEquals(66.0, result.getValue(1, 1), 0.0);
		assertEquals(77.0, result.getValue(1, 2), 0.0);
		assertEquals(88.0, result.getValue(1, 3), 0.0);
	}

}
