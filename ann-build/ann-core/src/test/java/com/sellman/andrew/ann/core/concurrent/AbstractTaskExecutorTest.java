package com.sellman.andrew.ann.core.concurrent;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Matchers.anyCollectionOf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractTaskExecutorTest {
	private AbstractTaskExecutor executor;

	private ThreadFactory threadFactory;

	private AbstractTask task1, task2;

	List<AbstractTask> tasks;

	@Before
	public void prepareTest() {
		threadFactory = new PrioritizedThreadFactory(Priority.NORMAL);
		executor = mock(AbstractTaskExecutor.class, CALLS_REAL_METHODS);
		doNothing().when(executor).doRunTasks(anyCollectionOf(AbstractTask.class));
		doNothing().when(executor).doRunTask(any(AbstractTask.class));

		task1 = mock(AbstractTask.class);
		task2 = mock(AbstractTask.class);

		tasks = new ArrayList<AbstractTask>(2);
		tasks.add(task1);
		tasks.add(task2);
	}

	@Test
	public void runTasks() throws InterruptedException {
		executor.runTasks(tasks);
		verify(executor).doRunTasks(tasks);
	}

	@Test
	public void runTask() throws InterruptedException {
		executor.runTask(task1);
		executor.runTask(task2);
		verify(executor).doRunTask(task1);
		verify(executor).doRunTask(task2);
	}

}
