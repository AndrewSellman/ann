package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.ParallelizableOperation1;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class ParallelizabeOperation1Factory extends ParallelizabeOperationFactory {
	private final ParallelizableOperation1Advisor advisor;

	public ParallelizabeOperation1Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public ParallelizabeOperation1Factory(TaskService taskService, ParallelizableOperation1Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation1Advisor getOperationAdvisor() {
		return advisor;
	}

	abstract public ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
