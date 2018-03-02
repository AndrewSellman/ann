package com.sellman.andrew.ann.core.math.factory;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class AdvisableParallelizabeOperationFactory {
	private static final int NO_LIMIT = -1;
	private static final int DEFAULT_TASK_POOL_LIMIT = NO_LIMIT;
	private static final int DEFAULT_MAX_IDLE_TASKS = 1000;
	private static final int DEFAULT_MIN_IDLE_TASKS = 250;
	private static final int DEFAULT_ADVICE_TEST_COUNT = 100;
	private final TaskService taskService;
	private final int taskPoolLimit;
	private final int maxIdleTasks;
	private final int minIdleTasks;
	private final int adviceTestCount;
	
	public AdvisableParallelizabeOperationFactory(TaskService taskService, int taskPoolLimit, int maxIdleTasks, int minIdleTasks, int adviceTestCount) {
		this.taskService = taskService;
		this.taskPoolLimit = taskPoolLimit;
		this.maxIdleTasks = maxIdleTasks;
		this.minIdleTasks = minIdleTasks;
		this.adviceTestCount = adviceTestCount;
	}

	public AdvisableParallelizabeOperationFactory(TaskService taskService) {
		this(taskService, DEFAULT_TASK_POOL_LIMIT, DEFAULT_MAX_IDLE_TASKS, DEFAULT_MIN_IDLE_TASKS, DEFAULT_ADVICE_TEST_COUNT);
	}

	protected GenericObjectPoolConfig getBasicObjectPoolConfiguration() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(getMinIdleTasks());
		config.setMaxIdle(getMaxIdleTasks());
		config.setMaxTotal(getTaskPoolLimit());
		return config;
	}

	protected int getTaskPoolLimit() {
		return taskPoolLimit;
	}

	protected int getMaxIdleTasks() {
		return maxIdleTasks;
	}

	protected int getMinIdleTasks() {
		return minIdleTasks;
	}

	protected int getAdviceTestCount() {
		return adviceTestCount;
	}

	protected TaskService getTaskService() {
		return taskService;
	}

}
