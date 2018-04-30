package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

public class ParallelizableOperation2Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation2Advisor(final int testCount, Cache<AdviceKey, Boolean> cache) {
		super(testCount, cache);
	}

	public ParallelizableOperation2Advisor(Cache<AdviceKey, Boolean> cache) {
		super(cache);
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixRowCount, final int matrixColumnCount, final Function function) {
		AdviceKey key = new AdviceKeyB(matrixRowCount, matrixColumnCount, function);
		Boolean asParallel = getAdvice(key);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < getOpTestCount(); t++) {
			Matrix m = getRandomMatrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += op.getSequentialOpNanos(m, function);
			parallelOpTotal += op.getParallelOpNanos(m, function);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getOpTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getOpTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
		return asParallel;
	}

}
