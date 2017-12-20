package com.sellman.andrew.ann.core.math;

import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MathSupport {
	private final TaskService taskService;

	public MathSupport(final TaskService taskService) {
		this.taskService = taskService;
	}

	protected void runTasks(final List<AbstractTask> tasks) {
		taskService.runTasks(tasks);
	}

	protected final Matrix runTasksAndReturnTarget(final List<AbstractTask> tasks, final Matrix target) {
		runTasks(tasks);
		return target;
	}

	protected final Vector runTasksAndReturnTarget(final List<AbstractTask> tasks, final Vector target) {
		runTasks(tasks);
		return target;
	}

}
