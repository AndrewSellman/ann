package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizabeOperation4Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation4Advisor advisor;

	public AdvisableParallelizabeOperation4Factory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, int adviceTestCount, final ParallelizableOperation4Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation4Factory(final TaskService taskService, final ParallelizableOperation4Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final ParallelizableOperation4Advisor getOperationAdvisor() {
		return advisor;
	}

	public abstract AdvisableParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
