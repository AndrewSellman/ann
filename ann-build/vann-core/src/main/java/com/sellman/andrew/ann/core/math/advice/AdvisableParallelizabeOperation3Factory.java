package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizabeOperation3Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation3Advisor advisor;

	public AdvisableParallelizabeOperation3Factory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, int adviceTestCount, final ParallelizableOperation3Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation3Factory(final TaskService taskService, final ParallelizableOperation3Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final ParallelizableOperation3Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
