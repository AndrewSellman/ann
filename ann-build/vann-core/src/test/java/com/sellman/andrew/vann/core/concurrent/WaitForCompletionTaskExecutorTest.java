package com.sellman.andrew.vann.core.concurrent;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.sellman.andrew.vann.core.concurrent.AbstractTask;
import com.sellman.andrew.vann.core.concurrent.PrioritizedThreadFactory;
import com.sellman.andrew.vann.core.concurrent.Priority;
import com.sellman.andrew.vann.core.concurrent.WaitForCompletionTaskExecutor;

public class WaitForCompletionTaskExecutorTest {
	private WaitForCompletionTaskExecutor executor;
	private ThreadFactory threadFactory;
	private AbstractTask task1, task2;
	private List<AbstractTask> tasks;

	@Before
	public void prepareTest() {
		threadFactory = new PrioritizedThreadFactory(Priority.NORMAL);
		executor = new WaitForCompletionTaskExecutor(threadFactory, 0);

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
	public void runTasks() {
		CountDownLatch taskGroup = new CountDownLatch(2);
		Whitebox.setInternalState(task1, "taskGroup", taskGroup);
		Whitebox.setInternalState(task2, "taskGroup", taskGroup);
		
		executor.runTasks(tasks);
		verify(task1).execute();
		verify(task2).execute();
	}

	@Test
	public void runTask() {
		CountDownLatch taskGroup = new CountDownLatch(1);
		Whitebox.setInternalState(task1, "taskGroup", taskGroup);
		
		executor.runTask(task1);
		verify(task1).execute();
	}

}
