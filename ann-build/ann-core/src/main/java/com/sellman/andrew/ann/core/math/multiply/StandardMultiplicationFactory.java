package com.sellman.andrew.ann.core.math.multiply;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.factory.ParallelizabeOperation1Factory;

public class StandardMultiplicationFactory extends ParallelizabeOperation1Factory {

	public StandardMultiplicationFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public StandardMultiplicationFactory(final TaskService taskService, final ParallelizableOperation1Advisor advisor) {
		super(taskService, advisor);
	}

	public final AdvisableParallelizableOperation1<StandardMultiplicationByRowTask, StandardMultiplicationByColumnTask> getOperation() {
		StandardMultiplicationByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		StandardMultiplicationByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new StandardMultiplication(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private StandardMultiplicationByRowTaskPool getByRowTaskPool() {
		return new StandardMultiplicationByRowTaskPool(getByRowTaskObjectPool());
	}

	private StandardMultiplicationByColumnTaskPool getByColumnTaskPool() {
		return new StandardMultiplicationByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<StandardMultiplicationByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<StandardMultiplicationByRowTask>(new StandardMultiplicationByRowTaskFactory(), config);
	}

	private GenericObjectPool<StandardMultiplicationByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<StandardMultiplicationByColumnTask>(new StandardMultiplicationByColumnTaskFactory(), config);
	}

}
