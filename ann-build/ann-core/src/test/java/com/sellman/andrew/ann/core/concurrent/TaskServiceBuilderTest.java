package com.sellman.andrew.ann.core.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class TaskServiceBuilderTest {
	private TaskServiceBuilder builder;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		builder = new TaskServiceBuilder();
	}

	@After
	public void finishTest() throws Exception {
		taskService.close();
	}

	@Test
	public void highPriorityWithWaitForCompletion() throws Exception {
		taskService = builder.highPriority().waitForCompletion().build();
		assertBuiltTaskService(Priority.HIGH, false);
	}

	@Test
	public void highPriorityWithFireAndForget() throws Exception {
		taskService = builder.highPriority().fireAndForget().build();
		assertBuiltTaskService(Priority.HIGH, true);
	}

	@Test
	public void highPriorityBuild() throws Exception {
		taskService = builder.highPriority().build();
		assertBuiltTaskService(Priority.HIGH, false);
	}

	@Test
	public void lowPriorityWithWaitForCompletion() throws Exception {
		taskService = builder.lowPriority().waitForCompletion().build();
		assertBuiltTaskService(Priority.LOW, false);
	}

	@Test
	public void lowPriorityWithFireAndForget() throws Exception {
		taskService = builder.lowPriority().fireAndForget().build();
		assertBuiltTaskService(Priority.LOW, true);
	}

	@Test
	public void lowPriority() throws Exception {
		taskService = builder.lowPriority().build();
		assertBuiltTaskService(Priority.LOW, false);
	}

	@Test
	public void normalPriorityWithWaitForCompletion() throws Exception {
		taskService = builder.normalPriority().waitForCompletion().build();
		assertBuiltTaskService(Priority.NORMAL, false);
	}

	@Test
	public void normalPriorityWithFireAndForget() throws Exception {
		taskService = builder.normalPriority().fireAndForget().build();
		assertBuiltTaskService(Priority.NORMAL, true);
	}

	@Test
	public void normalPriority() throws Exception {
		taskService = builder.normalPriority().build();
		assertBuiltTaskService(Priority.NORMAL, false);
	}

	@Test
	public void waitForCompletion() throws Exception {
		taskService = builder.waitForCompletion().build();
		assertBuiltTaskService(Priority.NORMAL, false);
	}

	@Test
	public void fireAndForget() throws Exception {
		taskService = builder.fireAndForget().build();
		assertBuiltTaskService(Priority.NORMAL, true);
	}

	@Test
	public void defaultBuild() throws Exception {
		taskService = builder.build();
		assertBuiltTaskService(Priority.NORMAL, false);
	}

	private void assertBuiltTaskService(Priority exepctedPriority, boolean isFireAndForgetTaskExecutor) {
		AbstractTaskExecutor taskExecutor = Whitebox.getInternalState(taskService, AbstractTaskExecutor.class);
		assertTaskExecutor(taskExecutor, isFireAndForgetTaskExecutor);

		ExecutorService executorService = Whitebox.getInternalState(taskExecutor, ExecutorService.class);
		assertExecutorService(executorService, exepctedPriority);
	}

	private void assertExecutorService(ExecutorService executorService, Priority exepctedPriority) {
		PrioritizedThreadFactory threadFactory = (PrioritizedThreadFactory) Whitebox.getInternalState(executorService, ThreadFactory.class);
		assertThreadFactory(threadFactory, exepctedPriority);
	}

	private void assertThreadFactory(PrioritizedThreadFactory threadFactory, Priority exepctedPriority) {
		assertEquals(exepctedPriority.getThreadPriority(), Whitebox.getInternalState(threadFactory, "threadPriority"));
		assertTrue(Whitebox.getInternalState(threadFactory, "threadNamePrefix").toString().contains(exepctedPriority.getDescription()));
	}

	private void assertTaskExecutor(AbstractTaskExecutor taskExecutor, boolean isFireAndForgetTaskExecutor) {
		if (isFireAndForgetTaskExecutor) {
			assertTrue(taskExecutor instanceof FireAndForgetTaskExecutor);
		} else {
			assertTrue(taskExecutor instanceof WaitForCompletionTaskExecutor);
		}
	}

}
