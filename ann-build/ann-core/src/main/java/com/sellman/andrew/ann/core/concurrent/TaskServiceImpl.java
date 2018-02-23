package com.sellman.andrew.ann.core.concurrent;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

class TaskServiceImpl implements TaskService {
	private final AbstractTaskExecutor taskExecutor;

	public TaskServiceImpl(AbstractTaskExecutor tasksExecutor) {
		this.taskExecutor = tasksExecutor;
	}

	@Override
	public void runTasks(List<? extends AbstractTask> tasks) {
		taskExecutor.runTasks(tasks);
	}

	@Override
	public void runTask(AbstractTask task) {
		taskExecutor.runTask(task);
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("TaskServiceImpl " + toString() + "was created.");
	}


	@PreDestroy
	@Override
	public void close() throws Exception {
		System.out.println("Closing TaskServiceImpl " + toString() + "...");
		taskExecutor.close();
		System.out.println("Closed TaskServiceImpl " + toString());
	}

}
