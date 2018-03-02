package com.sellman.andrew.ann.core.math.update;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation5;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.ann.core.math.factory.ParallelizabeOperation5Factory;

public class UpdationFactory extends ParallelizabeOperation5Factory {

	public UpdationFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation5Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public UpdationFactory(final TaskService taskService, final ParallelizableOperation5Advisor advisor) {
		super(taskService, advisor);
	}

	public AdvisableParallelizableOperation5<UpdationByRowTask, UpdationByColumnTask> getOperation() {
		UpdationByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		UpdationByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Updation(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private UpdationByRowTaskPool getByRowTaskPool() {
		return new UpdationByRowTaskPool(getByRowTaskObjectPool());
	}

	private UpdationByColumnTaskPool getByColumnTaskPool() {
		return new UpdationByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<UpdationByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<UpdationByRowTask>(new UpdationByRowTaskFactory(), config);
	}

	private GenericObjectPool<UpdationByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<UpdationByColumnTask>(new UpdationByColumnTaskFactory(), config);
	}

}
