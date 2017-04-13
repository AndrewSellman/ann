package com.sellman.andrew.ann.core.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadFactory;

class WaitForCompletionTaskExecutor extends AbstractTaskExecutor {

	public WaitForCompletionTaskExecutor(ThreadFactory threadFactory) {
		super(threadFactory);
	}

	@Override
	protected void doRunTask(AbstractTask task) {
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(1);
		tasks.add(task);
		doRunTasks(tasks);
	}

	@Override
	protected void doRunTasks(Collection<AbstractTask> tasks) {
		try {
			getExecutorService().invokeAll(tasks);
		} catch (InterruptedException ie) {
			throw new IllegalStateException("Unable to complete tasks.", ie);
		}
	}

}
