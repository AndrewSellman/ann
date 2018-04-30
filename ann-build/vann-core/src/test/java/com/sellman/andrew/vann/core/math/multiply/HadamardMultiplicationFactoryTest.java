package com.sellman.andrew.vann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplication;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByColumnTask;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByRowTask;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationFactory;

@RunWith(MockitoJUnitRunner.class)
public class HadamardMultiplicationFactoryTest {
	private static final int TASK_POOL_LIMIT = 42;
	private static final int MAX_IDLE_TASKS = 10;
	private static final int MIN_IDLE_TASKS = 5;
	private static final int ADVICE_TEST_COUNT = 101;

	@Mock
	private TaskService taskService;

	@Mock
	private ParallelizableOperation1Advisor advisor;

	@Test
	public void constructorWith6Arguements() {
		HadamardMultiplicationFactory factory = new HadamardMultiplicationFactory(taskService, TASK_POOL_LIMIT, MAX_IDLE_TASKS, MIN_IDLE_TASKS, ADVICE_TEST_COUNT, advisor);
		assertFactory(factory);
	}

	@Test
	public void constructorWith2Arguements() {
		HadamardMultiplicationFactory factory = new HadamardMultiplicationFactory(taskService, advisor);
		assertFactory(factory);
	}

	private void assertFactory(HadamardMultiplicationFactory factory) {
		AdvisableParallelizableOperation1<HadamardMultiplicationByRowTask, HadamardMultiplicationByColumnTask> operation = factory.getOperation();
		assertTrue(operation instanceof HadamardMultiplication);
		assertEquals(advisor, Whitebox.getInternalState(factory, ParallelizableOperation1Advisor.class));
		assertEquals(taskService, Whitebox.getInternalState(factory, TaskService.class));
	}

}
