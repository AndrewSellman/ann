package com.sellman.andrew.ann.core.concurrent;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AbstractTaskExecutorTest {
	private AbstractTaskExecutor executor;

	private AbstractTask task1, task2;

	List<AbstractTask> tasks;

	@Before
	public void prepareTest() {
		executor = mock(AbstractTaskExecutor.class, CALLS_REAL_METHODS);
		doNothing().when(executor).doRunTasks(anyListOf(AbstractTask.class));
		doNothing().when(executor).doRunTask(any(AbstractTask.class));

		task1 = mock(AbstractTask.class);
		task2 = mock(AbstractTask.class);

		tasks = new ArrayList<AbstractTask>(2);
		tasks.add(task1);
		tasks.add(task2);
	}

	@Test
	public void runTasks() {
		executor.runTasks(tasks);
		verify(executor).doRunTasks(tasks);
	}

	@Test
	public void runTask() {
		executor.runTask(task1);
		executor.runTask(task2);
		verify(executor).doRunTask(task1);
		verify(executor).doRunTask(task2);
	}

}
