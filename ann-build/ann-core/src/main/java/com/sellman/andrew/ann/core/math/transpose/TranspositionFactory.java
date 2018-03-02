package com.sellman.andrew.ann.core.math.transpose;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation3;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.ann.core.math.factory.AdvisableParallelizabeOperation3Factory;

public class TranspositionFactory extends AdvisableParallelizabeOperation3Factory {

	public TranspositionFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation3Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public TranspositionFactory(final TaskService taskService, final ParallelizableOperation3Advisor advisor) {
		super(taskService, advisor);
	}

	public AdvisableParallelizableOperation3<TranspositionByRowTask, TranspositionByColumnTask> getOperation() {
		TranspositionByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		TranspositionByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Transposition(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private TranspositionByRowTaskPool getByRowTaskPool() {
		return new TranspositionByRowTaskPool(getByRowTaskObjectPool());
	}

	private TranspositionByColumnTaskPool getByColumnTaskPool() {
		return new TranspositionByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<TranspositionByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<TranspositionByRowTask>(new TranspositionByRowTaskFactory(), config);
	}

	private GenericObjectPool<TranspositionByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<TranspositionByColumnTask>(new TranspositionByColumnTaskFactory(), config);
	}

}
