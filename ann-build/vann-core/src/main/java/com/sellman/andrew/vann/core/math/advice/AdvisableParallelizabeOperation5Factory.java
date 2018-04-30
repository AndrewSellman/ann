package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizabeOperation5Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation5Advisor advisor;

	public AdvisableParallelizabeOperation5Factory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, int adviceTestCount, final ParallelizableOperation5Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation5Factory(final TaskService taskService, final ParallelizableOperation5Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final ParallelizableOperation5Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
