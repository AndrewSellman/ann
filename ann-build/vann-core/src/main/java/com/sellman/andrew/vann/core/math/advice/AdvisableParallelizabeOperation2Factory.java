package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizabeOperation2Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation2Advisor advisor;

	public AdvisableParallelizabeOperation2Factory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation2Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation2Factory(final TaskService taskService, final ParallelizableOperation2Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final ParallelizableOperation2Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
