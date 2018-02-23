package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MathOperationsFactory {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 500;

	public MathOperations getOperations(final TaskService taskService) {
		StandardMultiplier standardMultiplier = getStandardMultiplier(taskService);
		HadamardMultiplier hadamardMultiplier = getHadamardMultiplier(taskService);
		Adder adder = getAdder(taskService);
		Subtractor subtractor = getSubtractor(taskService);
		Scaler scaler = getScaler(taskService);
		Manipulator manipulator = getManipulator(taskService);
		return new MathOperationsImpl(standardMultiplier, hadamardMultiplier, adder, subtractor, scaler, manipulator);
	}

	private Adder getAdder(TaskService taskService) {
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new Adder(taskService, advisor);
	}

	private Subtractor getSubtractor(TaskService taskService) {
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new Subtractor(taskService, advisor);
	}

	private Scaler getScaler(TaskService taskService) {
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new Scaler(taskService, advisor);
	}

	private Manipulator getManipulator(TaskService taskService) {
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new Manipulator(taskService, advisor);
	}

	private StandardMultiplier getStandardMultiplier(final TaskService taskService) {
		StandardMultiplyByRowTaskPool rowTaskPool = getStandardMultiplyByRowTaskPool();
		StandardMultiplyByColumnTaskPool columnTaskPool = getStandardMultiplyByColumnTaskPool();
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new StandardMultiplier(taskService, rowTaskPool, columnTaskPool, advisor);
	}

	private StandardMultiplyByColumnTaskPool getStandardMultiplyByColumnTaskPool() {
		return new StandardMultiplyByColumnTaskPool(getStandardMultiplyByColumnTaskObjectPool());
	}

	private StandardMultiplyByRowTaskPool getStandardMultiplyByRowTaskPool() {
		return new StandardMultiplyByRowTaskPool(getStandardMultiplyByRowTaskObjectPool());
	}

	private GenericObjectPool<StandardMultiplyByRowTask> getStandardMultiplyByRowTaskObjectPool() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(MIN_IDLE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(NO_LIMIT);
		return new GenericObjectPool<StandardMultiplyByRowTask>(new StandardMultiplyByRowTaskFactory(), config);
	}

	private GenericObjectPool<StandardMultiplyByColumnTask> getStandardMultiplyByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(MIN_IDLE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(NO_LIMIT);
		return new GenericObjectPool<StandardMultiplyByColumnTask>(new StandardMultiplyByColumnTaskFactory(), config);
	}

	private HadamardMultiplier getHadamardMultiplier(TaskService taskService) {
		HadamardMultiplyByRowTaskPool rowTaskPool = getHadamardMultiplyByRowTaskPool();
		HadamardMultiplyByColumnTaskPool columnTaskPool = getHadamardMultiplyByColumnTaskPool();
		ParallelizableOperationAdvisor advisor = new ParallelizableOperationAdvisor();
		return new HadamardMultiplier(taskService, rowTaskPool, columnTaskPool, advisor);
	}

	private HadamardMultiplyByColumnTaskPool getHadamardMultiplyByColumnTaskPool() {
		return new HadamardMultiplyByColumnTaskPool(getHadamardMultiplyByColumnTaskObjectPool());
	}

	private GenericObjectPool<HadamardMultiplyByColumnTask> getHadamardMultiplyByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(MIN_IDLE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(NO_LIMIT);
		return new GenericObjectPool<HadamardMultiplyByColumnTask>(new HadamardMultiplyByColumnTaskFactory(), config);
	}

	private HadamardMultiplyByRowTaskPool getHadamardMultiplyByRowTaskPool() {
		return new HadamardMultiplyByRowTaskPool(getHadamardMultiplyByRowTaskObjectPool());
	}

	private GenericObjectPool<HadamardMultiplyByRowTask> getHadamardMultiplyByRowTaskObjectPool() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(MIN_IDLE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(NO_LIMIT);
		return new GenericObjectPool<HadamardMultiplyByRowTask>(new HadamardMultiplyByRowTaskFactory(), config);
	}

}
