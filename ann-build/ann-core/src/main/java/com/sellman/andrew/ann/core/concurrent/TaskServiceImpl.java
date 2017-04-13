package com.sellman.andrew.ann.core.concurrent;

import java.util.Collection;

class TaskServiceImpl implements TaskService {
	private final AbstractTaskExecutor tasksExecutor;

	public TaskServiceImpl(AbstractTaskExecutor tasksExecutor) {
		this.tasksExecutor = tasksExecutor;
	}

	public void runTasks(Collection<AbstractTask> tasks) {
		tasksExecutor.runTasks(tasks);
	}

	public void runTask(AbstractTask task) {
		tasksExecutor.runTask(task);
	}

	public void close() throws Exception {
		tasksExecutor.close();
	}

}
