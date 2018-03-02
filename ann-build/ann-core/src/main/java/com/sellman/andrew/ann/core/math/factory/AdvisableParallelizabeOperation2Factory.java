package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation2;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class AdvisableParallelizabeOperation2Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation2Advisor advisor;

	public AdvisableParallelizabeOperation2Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation2Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation2Factory(TaskService taskService, ParallelizableOperation2Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation2Advisor getOperationAdvisor() {
		return advisor;
	}

	abstract public AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
