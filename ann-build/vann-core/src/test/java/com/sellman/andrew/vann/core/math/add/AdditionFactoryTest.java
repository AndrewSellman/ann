package com.sellman.andrew.vann.core.math.add;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.add.Addition;
import com.sellman.andrew.vann.core.math.add.AdditionByColumnTask;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTask;
import com.sellman.andrew.vann.core.math.add.AdditionFactory;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

@RunWith(MockitoJUnitRunner.class)
public class AdditionFactoryTest {
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
		AdditionFactory factory = new AdditionFactory(taskService, TASK_POOL_LIMIT, MAX_IDLE_TASKS, MIN_IDLE_TASKS, ADVICE_TEST_COUNT, advisor);
		assertFactory(factory);
	}

	@Test
	public void constructorWith2Arguements() {
		AdditionFactory factory = new AdditionFactory(taskService, advisor);
		assertFactory(factory);
	}

	private void assertFactory(AdditionFactory factory) {
		AdvisableParallelizableOperation1<AdditionByRowTask, AdditionByColumnTask> operation = factory.getOperation();
		assertTrue(operation instanceof Addition);
		assertEquals(advisor, Whitebox.getInternalState(factory, ParallelizableOperation1Advisor.class));
		assertEquals(taskService, Whitebox.getInternalState(factory, TaskService.class));
	}

}
