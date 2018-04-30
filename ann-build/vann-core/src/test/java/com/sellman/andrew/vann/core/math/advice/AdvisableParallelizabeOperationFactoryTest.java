package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertEquals;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperationFactory;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizabeOperationFactoryTest {
	private static final int NO_LIMIT = -1;
	private static final int DEFAULT_TASK_POOL_LIMIT = NO_LIMIT;
	private static final int DEFAULT_MAX_IDLE_TASKS = 1000;
	private static final int DEFAULT_MIN_IDLE_TASKS = 250;
	private static final int DEFAULT_ADVICE_TEST_COUNT = 100;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizabeOperationFactory factory;

	@Mock
	private TaskService taskService;

	@Before
	public void prepareTest() {
		Whitebox.setInternalState(factory, TaskService.class, taskService);
		Whitebox.setInternalState(factory, "adviceTestCount", DEFAULT_ADVICE_TEST_COUNT);
		Whitebox.setInternalState(factory, "minIdleTasks", DEFAULT_MIN_IDLE_TASKS);
		Whitebox.setInternalState(factory, "maxIdleTasks", DEFAULT_MAX_IDLE_TASKS);
		Whitebox.setInternalState(factory, "taskPoolLimit", DEFAULT_TASK_POOL_LIMIT);
	}

	@Test
	public void getBasicObjectPoolConfiguration() {
		GenericObjectPoolConfig config = factory.getBasicObjectPoolConfiguration();
		assertEquals(DEFAULT_MIN_IDLE_TASKS, config.getMinIdle());
		assertEquals(DEFAULT_MAX_IDLE_TASKS, config.getMaxIdle());
		assertEquals(DEFAULT_TASK_POOL_LIMIT, config.getMaxTotal());
	}

	@Test
	public void getTaskPoolLimit() {
		assertEquals(DEFAULT_TASK_POOL_LIMIT, factory.getTaskPoolLimit());
	}

	@Test
	public void getMaxIdleTasks() {
		assertEquals(DEFAULT_MAX_IDLE_TASKS, factory.getMaxIdleTasks());
	}

	@Test
	public void getMinIdleTasks() {
		assertEquals(DEFAULT_MIN_IDLE_TASKS, factory.getMinIdleTasks());
	}

	@Test
	public void getAdviseTestCount() {
		assertEquals(DEFAULT_ADVICE_TEST_COUNT, factory.getAdviceTestCount());
	}

	@Test
	public void getTaskService() {
		assertEquals(taskService, factory.getTaskService());
	}

}
