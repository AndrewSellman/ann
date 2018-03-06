package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class AdvisableParallelizabeOperation1Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation1Advisor advisor;

	public AdvisableParallelizabeOperation1Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation1Factory(TaskService taskService, ParallelizableOperation1Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation1Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
