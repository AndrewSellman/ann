package com.sellman.andrew.ann.core.math.sum;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation4;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.ann.core.math.factory.AdvisableParallelizabeOperation4Factory;

public class SummationFactory extends AdvisableParallelizabeOperation4Factory {

	public SummationFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount, final ParallelizableOperation4Advisor advisor) {
		super(taskService, taskPoolLimit, maxIdleTasks, minIdleTasks, adviceTestCount, advisor);
	}

	public SummationFactory(final TaskService taskService, final ParallelizableOperation4Advisor advisor) {
		super(taskService, advisor);
	}

	public final AdvisableParallelizableOperation4<SummationByRowTask, SummationByColumnTask> getOperation() {
		SummationByRowTaskPool opByRowTaskPool = getByRowTaskPool();
		SummationByColumnTaskPool opByColumnTaskPool = getByColumnTaskPool();
		return new Summation(getTaskService(), opByRowTaskPool, opByColumnTaskPool, getOperationAdvisor());
	}

	private SummationByRowTaskPool getByRowTaskPool() {
		return new SummationByRowTaskPool(getByRowTaskObjectPool());
	}

	private SummationByColumnTaskPool getByColumnTaskPool() {
		return new SummationByColumnTaskPool(getByColumnTaskObjectPool());
	}

	private GenericObjectPool<SummationByRowTask> getByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SummationByRowTask>(new SummationByRowTaskFactory(), config);
	}

	private GenericObjectPool<SummationByColumnTask> getByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SummationByColumnTask>(new SummationByColumnTaskFactory(), config);
	}

}
