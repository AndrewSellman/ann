package com.sellman.andrew.vann.core.math.add;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

public class AdditionFactory extends AdvisableParallelizabeOperation1Factory {

	public AdditionFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public AdditionFactory(final TaskService taskService, final ParallelizableOperation1Advisor advisor) {
		super(taskService, advisor);
	}

	public AdvisableParallelizableOperation1<AdditionByRowTask, AdditionByColumnTask> getOperation() {
		AdditionByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		AdditionByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Addition(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private AdditionByRowTaskPool getByRowTaskPool() {
		return new AdditionByRowTaskPool(getByRowTaskObjectPool());
	}

	private AdditionByColumnTaskPool getByColumnTaskPool() {
		return new AdditionByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<AdditionByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<AdditionByRowTask>(new AdditionByRowTaskFactory(), config);
	}

	private GenericObjectPool<AdditionByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<AdditionByColumnTask>(new AdditionByColumnTaskFactory(), config);
	}

}
