package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation5;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class AdvisableParallelizabeOperation5Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation5Advisor advisor;

	public AdvisableParallelizabeOperation5Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation5Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation5Factory(TaskService taskService, ParallelizableOperation5Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation5Advisor getOperationAdvisor() {
		return advisor;
	}

	abstract public AdvisableParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
