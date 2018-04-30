package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;

@RunWith(MockitoJUnitRunner.class)
public class StandardMultiplicationFactoryTest {
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
		StandardMultiplicationFactory factory = new StandardMultiplicationFactory(taskService, TASK_POOL_LIMIT, MAX_IDLE_TASKS, MIN_IDLE_TASKS, ADVICE_TEST_COUNT, advisor);
		assertFactory(factory);
	}

	@Test
	public void constructorWith2Arguements() {
		StandardMultiplicationFactory factory = new StandardMultiplicationFactory(taskService, advisor);
		assertFactory(factory);
	}

	private void assertFactory(StandardMultiplicationFactory factory) {
		AdvisableParallelizableOperation1<StandardMultiplicationByRowTask, StandardMultiplicationByColumnTask> operation = factory.getOperation();
		assertTrue(operation instanceof StandardMultiplication);
		assertEquals(advisor, Whitebox.getInternalState(factory, ParallelizableOperation1Advisor.class));
		assertEquals(taskService, Whitebox.getInternalState(factory, TaskService.class));
	}

}
