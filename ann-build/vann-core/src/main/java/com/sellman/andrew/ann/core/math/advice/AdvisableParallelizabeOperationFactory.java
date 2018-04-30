package com.sellman.andrew.ann.core.math.advice;

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

	public AdvisableParallelizabeOperationFactory(final TaskService taskService, final int taskPoolLimit, final int maxIdleTasks, final int minIdleTasks, final int adviceTestCount) {
		this.taskService = taskService;
		this.taskPoolLimit = taskPoolLimit;
		this.maxIdleTasks = maxIdleTasks;
		this.minIdleTasks = minIdleTasks;
		this.adviceTestCount = adviceTestCount;
	}

	public AdvisableParallelizabeOperationFactory(final TaskService taskService) {
		this(taskService, DEFAULT_TASK_POOL_LIMIT, DEFAULT_MAX_IDLE_TASKS, DEFAULT_MIN_IDLE_TASKS, DEFAULT_ADVICE_TEST_COUNT);
	}

	protected final GenericObjectPoolConfig getBasicObjectPoolConfiguration() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(getMinIdleTasks());
		config.setMaxIdle(getMaxIdleTasks());
		config.setMaxTotal(getTaskPoolLimit());
		return config;
	}

	protected final int getTaskPoolLimit() {
		return taskPoolLimit;
	}

	protected final int getMaxIdleTasks() {
		return maxIdleTasks;
	}

	protected final int getMinIdleTasks() {
		return minIdleTasks;
	}

	protected final int getAdviceTestCount() {
		return adviceTestCount;
	}

	protected final TaskService getTaskService() {
		return taskService;
	}

}
