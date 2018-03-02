package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.ParallelizableOperation3;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class ParallelizabeOperation3Factory extends ParallelizabeOperationFactory {
	private final ParallelizableOperation3Advisor advisor;

	public ParallelizabeOperation3Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation3Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public ParallelizabeOperation3Factory(TaskService taskService, ParallelizableOperation3Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation3Advisor getOperationAdvisor() {
		return advisor;
	}

	abstract public ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
