package com.sellman.andrew.ann.core.concurrent;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FireAndForgetTaskExecutorTest {
	private FireAndForgetTaskExecutor executor;

	private ThreadFactory threadFactory;

	private AbstractTask task1, task2;

	List<AbstractTask> tasks;

	@Before
	public void prepareTest() {
		threadFactory = new PrioritizedThreadFactory(Priority.NORMAL);
		executor = new FireAndForgetTaskExecutor(threadFactory, 0);

		task1 = mock(AbstractTask.class, CALLS_REAL_METHODS);
		doNothing().when(task1).execute();

		task2 = mock(AbstractTask.class, CALLS_REAL_METHODS);
		doNothing().when(task2).execute();

		tasks = new ArrayList<AbstractTask>(2);
		tasks.add(task1);
		tasks.add(task2);
	}

	@After
	public void finishTest() throws Exception {
		executor.close();
	}

	@Test
	public void runTasks() throws InterruptedException {
		executor.runTasks(tasks);
		Thread.sleep(10);
		verify(task1).execute();
		verify(task2).execute();
	}

	@Test
	public void runTask() throws InterruptedException {
		executor.runTask(task1);
		Thread.sleep(10);
		verify(task1).execute();
	}

}
