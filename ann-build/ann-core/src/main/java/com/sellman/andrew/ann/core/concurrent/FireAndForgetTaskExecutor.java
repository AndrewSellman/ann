package com.sellman.andrew.ann.core.concurrent;

import java.util.Collection;
import java.util.concurrent.ThreadFactory;

class FireAndForgetTaskExecutor extends AbstractTaskExecutor {

	public FireAndForgetTaskExecutor(ThreadFactory threadFactory) {
		super(threadFactory);
	}

	@Override
	protected void doRunTask(AbstractTask task) {
		getExecutorService().submit(task);
	}

	@Override
	protected void doRunTasks(Collection<AbstractTask> tasks) {
		for (AbstractTask task : tasks) {
			doRunTask(task);
		}

	}

}
