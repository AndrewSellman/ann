package com.sellman.andrew.ann.core.math.scale;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizabeOperation2Factory;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation2;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation2Advisor;

public class ScalerFactory extends AdvisableParallelizabeOperation2Factory {

	public ScalerFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation2Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public ScalerFactory(final TaskService taskService, final ParallelizableOperation2Advisor advisor) {
		super(taskService, advisor);
	}

	public AdvisableParallelizableOperation2<ScalerByRowTask, ScalerByColumnTask> getOperation() {
		ScalerByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		ScalerByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Scaler(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private ScalerByRowTaskPool getByRowTaskPool() {
		return new ScalerByRowTaskPool(getByRowTaskObjectPool());
	}

	private ScalerByColumnTaskPool getByColumnTaskPool() {
		return new ScalerByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<ScalerByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<ScalerByRowTask>(new ScalerByRowTaskFactory(), config);
	}

	private GenericObjectPool<ScalerByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<ScalerByColumnTask>(new ScalerByColumnTaskFactory(), config);
	}

}
