package com.sellman.andrew.ann.core.math.factory;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation4;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

abstract public class AdvisableParallelizabeOperation4Factory extends AdvisableParallelizabeOperationFactory {
	private final ParallelizableOperation4Advisor advisor;

	public AdvisableParallelizabeOperation4Factory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount, ParallelizableOperation4Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount);
		this.advisor = advisor;
	}

	public AdvisableParallelizabeOperation4Factory(TaskService taskService, ParallelizableOperation4Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected ParallelizableOperation4Advisor getOperationAdvisor() {
		return advisor;
	}

	abstract public AdvisableParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getOperation();

}
