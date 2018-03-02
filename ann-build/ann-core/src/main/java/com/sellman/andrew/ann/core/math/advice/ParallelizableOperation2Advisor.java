package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public class ParallelizableOperation2Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation2Advisor(final int testCount) {
		super(testCount);
	}

	public ParallelizableOperation2Advisor() {
		super();
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixRowCount, final int matrixColumnCount, final Function function) {
		AdviceKeyB key = new AdviceKeyB(matrixRowCount, matrixColumnCount, function);
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
