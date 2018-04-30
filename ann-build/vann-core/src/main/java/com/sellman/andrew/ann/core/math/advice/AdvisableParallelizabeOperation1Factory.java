package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizabeOperation1Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation1Advisor advisor;

	public AdvisableParallelizabeOperation1Factory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation1Factory(final TaskService taskService, final ParallelizableOperation1Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final ParallelizableOperation1Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
