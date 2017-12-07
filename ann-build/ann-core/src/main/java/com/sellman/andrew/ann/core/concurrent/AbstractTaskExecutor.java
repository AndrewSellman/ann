package com.sellman.andrew.ann.core.concurrent;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

abstract class AbstractTaskExecutor {
	private final ExecutorService executorService;

	public AbstractTaskExecutor(ThreadFactory threadFactory, int threadCount) {
		if (threadCount < 0) {
			throw new IllegalArgumentException("threadCount cannot be less tan zero!");
		}
		
		if (threadCount == 0) {
			this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);
		} else {
			this.executorService = Executors.newFixedThreadPool(threadCount, threadFactory);
		}
	}

	public final void runTasks(Collection<? extends AbstractTask> tasks) {
		doRunTasks(tasks);
	}

	public final void runTask(AbstractTask task) {
		doRunTask(task);
	}

	public void close() throws Exception {
		executorService.shutdown();
		executorService.shutdownNow();
	}

	protected ExecutorService getExecutorService() {
		return executorService;
	}

	protected abstract void doRunTask(AbstractTask task);

	protected abstract void doRunTasks(Collection<? extends AbstractTask> tasks);

}
