package com.sellman.andrew.vann.core.math.subtract;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

public class SubtractionFactory extends AdvisableParallelizabeOperation1Factory {

	public SubtractionFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation1Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public SubtractionFactory(final TaskService taskService, final ParallelizableOperation1Advisor advisor) {
		super(taskService, advisor);
	}

	@Override
	public AdvisableParallelizableOperation1<SubtractionByRowTask, SubtractionByColumnTask> getOperation() {
		SubtractionByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		SubtractionByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Subtraction(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private SubtractionByRowTaskPool getByRowTaskPool() {
		return new SubtractionByRowTaskPool(getByRowTaskObjectPool());
	}

	private SubtractionByColumnTaskPool getByColumnTaskPool() {
		return new SubtractionByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<SubtractionByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SubtractionByRowTask>(new SubtractionByRowTaskFactory(), config);
	}

	private GenericObjectPool<SubtractionByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SubtractionByColumnTask>(new SubtractionByColumnTaskFactory(), config);
	}

}
