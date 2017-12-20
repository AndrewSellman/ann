package com.sellman.andrew.ann.core.concurrent;

import java.util.List;

class TaskServiceImpl implements TaskService {
	private final AbstractTaskExecutor taskExecutor;

	public TaskServiceImpl(AbstractTaskExecutor tasksExecutor) {
		this.taskExecutor = tasksExecutor;
	}

	public void runTasks(List<? extends AbstractTask> tasks) {
		taskExecutor.runTasks(tasks);
	}

	public void runTask(AbstractTask task) {
		taskExecutor.runTask(task);
	}

	public void close() throws Exception {
		taskExecutor.close();
	}

}
