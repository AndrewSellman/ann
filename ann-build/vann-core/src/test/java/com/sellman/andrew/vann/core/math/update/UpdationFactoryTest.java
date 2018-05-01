package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation5;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;

@RunWith(MockitoJUnitRunner.class)
public class UpdationFactoryTest {
	private static final int TASK_POOL_LIMIT = 42;
	private static final int MAX_IDLE_TASKS = 10;
	private static final int MIN_IDLE_TASKS = 5;
	private static final int ADVICE_TEST_COUNT = 101;

	@Mock
	private TaskService taskService;

	@Mock
	private ParallelizableOperation5Advisor advisor;

	@Test
	public void constructorWith6Arguements() {
		UpdationFactory factory = new UpdationFactory(taskService, TASK_POOL_LIMIT, MAX_IDLE_TASKS, MIN_IDLE_TASKS, ADVICE_TEST_COUNT, advisor);
		assertFactory(factory);
	}

	@Test
	public void constructorWith2Arguements() {
		UpdationFactory factory = new UpdationFactory(taskService, advisor);
		assertFactory(factory);
	}

	private void assertFactory(UpdationFactory factory) {
		AdvisableParallelizableOperation5<UpdationByRowTask, UpdationByColumnTask> operation = factory.getOperation();
		assertTrue(operation instanceof Updation);
		assertEquals(advisor, Whitebox.getInternalState(factory, ParallelizableOperation5Advisor.class));
		assertEquals(taskService, Whitebox.getInternalState(factory, TaskService.class));
	}

}
