package com.sellman.andrew.vann.core.math.transpose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation3;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;

@RunWith(MockitoJUnitRunner.class)
public class TranspositionFactoryTest {
	private static final int TASK_POOL_LIMIT = 42;
	private static final int MAX_IDLE_TASKS = 10;
	private static final int MIN_IDLE_TASKS = 5;
	private static final int ADVICE_TEST_COUNT = 101;

	@Mock
	private TaskService taskService;

	@Mock
	private ParallelizableOperation3Advisor advisor;

	@Test
	public void constructorWith6Arguements() {
		TranspositionFactory factory = new TranspositionFactory(taskService, TASK_POOL_LIMIT, MAX_IDLE_TASKS, MIN_IDLE_TASKS, ADVICE_TEST_COUNT, advisor);
		assertFactory(factory);
	}

	@Test
	public void constructorWith2Arguements() {
		TranspositionFactory factory = new TranspositionFactory(taskService, advisor);
		assertFactory(factory);
	}

	private void assertFactory(TranspositionFactory factory) {
		AdvisableParallelizableOperation3<TranspositionByRowTask, TranspositionByColumnTask> operation = factory.getOperation();
		assertTrue(operation instanceof Transposition);
		assertEquals(advisor, Whitebox.getInternalState(factory, ParallelizableOperation3Advisor.class));
		assertEquals(taskService, Whitebox.getInternalState(factory, TaskService.class));
	}

}
