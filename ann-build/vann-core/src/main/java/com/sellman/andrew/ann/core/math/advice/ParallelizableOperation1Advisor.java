package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.cache.Cache;

public class ParallelizableOperation1Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation1Advisor(final int testCount, Cache<AdviceKey, Boolean> cache) {
		super(testCount, cache);
	}

	public ParallelizableOperation1Advisor(Cache<AdviceKey, Boolean> cache) {
		super(cache);
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixARowCount, final int matrixAColumnCount, final int matrixBRowCount, final int matrixBColumnCount) {
		AdviceKey key = new AdviceKeyA(matrixARowCount, matrixAColumnCount, matrixBRowCount, matrixBColumnCount);
		Boolean asParallel = getAdvice(key);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < getOpTestCount(); t++) {
			Matrix a = getRandomMatrix(matrixARowCount, matrixAColumnCount);
			Matrix b = getRandomMatrix(matrixBRowCount, matrixBColumnCount);

			sequentialOpTotal += op.getSequentialOpNanos(a, b);
			parallelOpTotal += op.getParallelOpNanos(a, b);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getOpTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getOpTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
		return asParallel;
	}

}
