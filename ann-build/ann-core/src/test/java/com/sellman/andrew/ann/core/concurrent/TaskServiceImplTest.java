package com.sellman.andrew.ann.core.concurrent;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {
	private TaskServiceImpl service;

	@Mock
	private AbstractTaskExecutor executor;

	@Mock
	private AbstractTask task;

	@Mock
	private List<AbstractTask> tasks;

	@Before
	public void prepareTest() {
		service = new TaskServiceImpl(executor);
	}

	@Test
	public void runTasks() {
		service.runTasks(tasks);
		verify(executor).runTasks(tasks);
	}

	@Test
	public void runTask() {
		service.runTask(task);
		verify(executor).runTask(task);
	}

	@Test
	public void close() throws Exception {
		service.close();
		verify(executor).close();
	}

}
