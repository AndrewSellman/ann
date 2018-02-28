package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MathOperationsFactory {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 250;

	public MathOperations getOperations(final TaskService taskService) {
		StandardMultiplication standardMultiplier = getStandardMultiplication(taskService);
		HadamardMultiplication hadamardMultiplier = getHadamardMultiplication(taskService);
		Addition adder = getAddition(taskService);
		Summation summation = getSummation(taskService);
		Subtraction subtractor = getSubtraction(taskService);
		Scaler scaler = getScaler(taskService);
		Transposition transposition = getTransposition(taskService);
		Updation updation = getUpdation(taskService);
		return new MathOperationsImpl(standardMultiplier, hadamardMultiplier, adder, summation, subtractor, scaler, transposition, updation);
	}

	private Addition getAddition(TaskService taskService) {
		AdditionByRowTaskPool opByRowTaskPool = getAdditionByRowTaskPool();
		AdditionByColumnTaskPool opByColumnTaskPool = getAdditionByColumnTaskPool();
		ParallelizableOperation1Advisor advisor = new ParallelizableOperation1Advisor();
		return new Addition(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private AdditionByRowTaskPool getAdditionByRowTaskPool() {
		return new AdditionByRowTaskPool(getAdditionByRowTaskObjectPool());
	}

	private AdditionByColumnTaskPool getAdditionByColumnTaskPool() {
		return new AdditionByColumnTaskPool(getAdditionByColumnTaskObjectPool());
	}

	private GenericObjectPool<AdditionByRowTask> getAdditionByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<AdditionByRowTask>(new AdditionByRowTaskFactory(), config);
	}

	private GenericObjectPool<AdditionByColumnTask> getAdditionByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<AdditionByColumnTask>(new AdditionByColumnTaskFactory(), config);
	}

	private Subtraction getSubtraction(TaskService taskService) {
		SubtractionByRowTaskPool opByRowTaskPool = getSubtractionByRowTaskPool();
		SubtractionByColumnTaskPool opByColumnTaskPool = getSubtractionByColumnTaskPool();
		ParallelizableOperation1Advisor advisor = new ParallelizableOperation1Advisor();
		return new Subtraction(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private SubtractionByColumnTaskPool getSubtractionByColumnTaskPool() {
		return new SubtractionByColumnTaskPool(getSubtractionByColumnTaskObjectPool());
	}

	private GenericObjectPool<SubtractionByColumnTask> getSubtractionByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SubtractionByColumnTask>(new SubtractionByColumnTaskFactory(), config);
	}

	private SubtractionByRowTaskPool getSubtractionByRowTaskPool() {
		return new SubtractionByRowTaskPool(getSubtractionByRowTaskObjectPool());
	}

	private GenericObjectPool<SubtractionByRowTask> getSubtractionByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SubtractionByRowTask>(new SubtractionByRowTaskFactory(), config);
	}

	private Scaler getScaler(TaskService taskService) {
		ScalerByRowTaskPool opByRowTaskPool = getScalerByRowTaskPool();
		ScalerByColumnTaskPool opByColumnTaskPool = getScalerByColumnTaskPool();
		ParallelizableOperation2Advisor advisor = new ParallelizableOperation2Advisor();
		return new Scaler(taskService, advisor, opByRowTaskPool, opByColumnTaskPool);
	}

	private ScalerByRowTaskPool getScalerByRowTaskPool() {
		return new ScalerByRowTaskPool(getScaleByRowTaskObjectPool());
	}

	private ScalerByColumnTaskPool getScalerByColumnTaskPool() {
		return new ScalerByColumnTaskPool(getScaleByColumnTaskObjectPool());
	}

	private GenericObjectPool<ScalerByRowTask> getScaleByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<ScalerByRowTask>(new ScalerByRowTaskFactory(), config);
	}

	private GenericObjectPool<ScalerByColumnTask> getScaleByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<ScalerByColumnTask>(new ScalerByColumnTaskFactory(), config);
	}

	private StandardMultiplication getStandardMultiplication(final TaskService taskService) {
		StandardMultiplicationByRowTaskPool opByRowTaskPool = getStandardMultiplicationByRowTaskPool();
		StandardMultiplicationByColumnTaskPool opByColumnTaskPool = getStandardMultiplicationByColumnTaskPool();
		ParallelizableOperation1Advisor advisor = new ParallelizableOperation1Advisor();
		return new StandardMultiplication(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private StandardMultiplicationByColumnTaskPool getStandardMultiplicationByColumnTaskPool() {
		return new StandardMultiplicationByColumnTaskPool(getStandardMultiplicationByColumnTaskObjectPool());
	}

	private StandardMultiplicationByRowTaskPool getStandardMultiplicationByRowTaskPool() {
		return new StandardMultiplicationByRowTaskPool(getStandardMultiplicationByRowTaskObjectPool());
	}

	private GenericObjectPool<StandardMultiplicationByRowTask> getStandardMultiplicationByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<StandardMultiplicationByRowTask>(new StandardMultiplicationByRowTaskFactory(), config);
	}

	private GenericObjectPool<StandardMultiplicationByColumnTask> getStandardMultiplicationByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<StandardMultiplicationByColumnTask>(new StandardMultiplicationByColumnTaskFactory(), config);
	}

	private HadamardMultiplication getHadamardMultiplication(TaskService taskService) {
		HadamardMultiplicationByRowTaskPool opByRowTaskPool = getHadamardMultiplicationByRowTaskPool();
		HadamardMultiplicationByColumnTaskPool opByColumnTaskPool = getHadamardMultiplicationByColumnTaskPool();
		ParallelizableOperation1Advisor advisor = new ParallelizableOperation1Advisor();
		return new HadamardMultiplication(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private HadamardMultiplicationByColumnTaskPool getHadamardMultiplicationByColumnTaskPool() {
		return new HadamardMultiplicationByColumnTaskPool(getHadamardMultiplicationByColumnTaskObjectPool());
	}

	private GenericObjectPool<HadamardMultiplicationByColumnTask> getHadamardMultiplicationByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<HadamardMultiplicationByColumnTask>(new HadamardMultiplicationByColumnTaskFactory(), config);
	}

	private HadamardMultiplicationByRowTaskPool getHadamardMultiplicationByRowTaskPool() {
		return new HadamardMultiplicationByRowTaskPool(getHadamardMultiplicationByRowTaskObjectPool());
	}

	private GenericObjectPool<HadamardMultiplicationByRowTask> getHadamardMultiplicationByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<HadamardMultiplicationByRowTask>(new HadamardMultiplicationByRowTaskFactory(), config);
	}

	private Transposition getTransposition(TaskService taskService) {
		TranspositionByRowTaskPool opByRowTaskPool = getTranspositionByRowTaskPool();
		TranspositionByColumnTaskPool opByColumnTaskPool = getTranspositionByColumnTaskPool();
		ParallelizableOperation3Advisor advisor = new ParallelizableOperation3Advisor();
		return new Transposition(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private TranspositionByColumnTaskPool getTranspositionByColumnTaskPool() {
		return new TranspositionByColumnTaskPool(getTranspositionByColumnTaskObjectPool());
	}

	private GenericObjectPool<TranspositionByColumnTask> getTranspositionByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<TranspositionByColumnTask>(new TranspositionByColumnTaskFactory(), config);
	}

	private TranspositionByRowTaskPool getTranspositionByRowTaskPool() {
		return new TranspositionByRowTaskPool(getTranspositionByRowTaskObjectPool());
	}

	private GenericObjectPool<TranspositionByRowTask> getTranspositionByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<TranspositionByRowTask>(new TranspositionByRowTaskFactory(), config);
	}

	private Summation getSummation(TaskService taskService) {
		SummationByRowTaskPool opByRowTaskPool = getSummationByRowTaskPool();
		SummationByColumnTaskPool opByColumnTaskPool = getSummationByColumnTaskPool();
		ParallelizableOperation4Advisor advisor = new ParallelizableOperation4Advisor();
		return new Summation(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private SummationByColumnTaskPool getSummationByColumnTaskPool() {
		return new SummationByColumnTaskPool(getSummationByColumnTaskObjectPool());
	}

	private GenericObjectPool<SummationByColumnTask> getSummationByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SummationByColumnTask>(new SummationByColumnTaskFactory(), config);
	}

	private SummationByRowTaskPool getSummationByRowTaskPool() {
		return new SummationByRowTaskPool(getSummationByRowTaskObjectPool());
	}

	private GenericObjectPool<SummationByRowTask> getSummationByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<SummationByRowTask>(new SummationByRowTaskFactory(), config);
	}

	private Updation getUpdation(TaskService taskService) {
		UpdationByRowTaskPool opByRowTaskPool = getUpdationByRowTaskPool();
		UpdationByColumnTaskPool opByColumnTaskPool = getUpdationByColumnTaskPool();
		ParallelizableOperation5Advisor advisor = new ParallelizableOperation5Advisor();
		return new Updation(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	private UpdationByColumnTaskPool getUpdationByColumnTaskPool() {
		return new UpdationByColumnTaskPool(getUpdationByColumnTaskObjectPool());
	}

	private GenericObjectPool<UpdationByColumnTask> getUpdationByColumnTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<UpdationByColumnTask>(new UpdationByColumnTaskFactory(), config);
	}

	private UpdationByRowTaskPool getUpdationByRowTaskPool() {
		return new UpdationByRowTaskPool(getUpdationByRowTaskObjectPool());
	}

	private GenericObjectPool<UpdationByRowTask> getUpdationByRowTaskObjectPool() {
		GenericObjectPoolConfig config = getBasicObjectPoolConfiguration();
		return new GenericObjectPool<UpdationByRowTask>(new UpdationByRowTaskFactory(), config);
	}

	private GenericObjectPoolConfig getBasicObjectPoolConfiguration() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(MIN_IDLE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxTotal(NO_LIMIT);
		return config;
	}

}
