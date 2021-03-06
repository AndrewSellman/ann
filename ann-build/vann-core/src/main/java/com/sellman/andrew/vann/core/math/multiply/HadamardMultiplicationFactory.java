package com.sellman.andrew.vann.core.math.multiply;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

public class HadamardMultiplicationFactory extends AdvisableParallelizabeOperation1Factory {

	public HadamardMultiplicationFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public HadamardMultiplicationFactory(final TaskService taskService, final ParallelizableOperation1Advisor advisor) {
		super(taskService, advisor);
	}

	@Override
	public AdvisableParallelizableOperation1<HadamardMultiplicationByRowTask, HadamardMultiplicationByColumnTask> getOperation() {
		HadamardMultiplicationByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		HadamardMultiplicationByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new HadamardMultiplication(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private HadamardMultiplicationByRowTaskPool getByRowTaskPool() {
		return new HadamardMultiplicationByRowTaskPool(getByRowTaskObjectPool());
	}

	private HadamardMultiplicationByColumnTaskPool getByColumnTaskPool() {
		return new HadamardMultiplicationByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<HadamardMultiplicationByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<HadamardMultiplicationByRowTask>(new HadamardMultiplicationByRowTaskFactory(), config);
	}

	private GenericObjectPool<HadamardMultiplicationByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<HadamardMultiplicationByColumnTask>(new HadamardMultiplicationByColumnTaskFactory(), config);
	}

}
