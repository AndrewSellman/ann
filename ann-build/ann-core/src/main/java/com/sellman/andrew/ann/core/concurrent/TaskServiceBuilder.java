package com.sellman.andrew.ann.core.concurrent;

import java.util.concurrent.ThreadFactory;

public class TaskServiceBuilder {
	private Priority priority;
	private boolean fireAndForget;

	public TaskServiceBuilder() {
		this.normalPriority().waitForCompletion();
	}

	public TaskService build() {
		ThreadFactory threadFactory = new PrioritizedThreadFactory(priority);
		AbstractTaskExecutor taskExecutor = buildTaskExecutor(threadFactory);
		return new TaskServiceImpl(taskExecutor);
	}

	public TaskServiceBuilder waitForCompletion() {
		fireAndForget = false;
		return this;
	}

	public TaskServiceBuilder fireAndForget() {
		fireAndForget = true;
		return this;
	}

	public TaskServiceBuilder normalPriority() {
		this.priority = Priority.NORMAL;
		return this;
	}

	public TaskServiceBuilder lowPriority() {
		this.priority = Priority.LOW;
		return this;
	}

	public TaskServiceBuilder highPriority() {
		this.priority = Priority.HIGH;
		return this;
	}

	private AbstractTaskExecutor buildTaskExecutor(ThreadFactory threadFactory) {
		if (fireAndForget) {
			return new FireAndForgetTaskExecutor(threadFactory);
		} else {
			return new WaitForCompletionTaskExecutor(threadFactory);
		}
	}

}
