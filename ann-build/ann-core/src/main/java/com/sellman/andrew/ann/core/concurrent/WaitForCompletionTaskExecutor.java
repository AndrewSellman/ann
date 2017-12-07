package com.sellman.andrew.ann.core.concurrent;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadFactory;

class WaitForCompletionTaskExecutor extends AbstractTaskExecutor {

	public WaitForCompletionTaskExecutor(ThreadFactory threadFactory, int threadCount) {
		super(threadFactory, threadCount);
	}

	@Override
	protected void doRunTask(AbstractTask task) {
		doRunTasks(Arrays.asList(task));
	}

	@Override
	protected void doRunTasks(Collection<? extends AbstractTask> tasks) {
		try {
			getExecutorService().invokeAll(tasks);
		} catch (InterruptedException ie) {
			throw new IllegalStateException("Unable to complete tasks.", ie);
		}
	}

}
