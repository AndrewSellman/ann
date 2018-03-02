package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public class ParallelizableOperation5Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation5Advisor(final int testCount) {
		super(testCount);
	}

	public ParallelizableOperation5Advisor() {
		super();
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixRowCount, final int matrixColumnCount) {
		AdviceKeyC key = new AdviceKeyC(matrixRowCount, matrixColumnCount);
		Boolean asParallel = getAdvice(key);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < getOpTestCount(); t++) {
			Matrix source = getRandomMatrix(matrixRowCount, matrixColumnCount);
			Matrix target = new Matrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += op.getSequentialOpNanos(source, target);
			parallelOpTotal += op.getParallelOpNanos(source, target);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getOpTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getOpTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
		return asParallel;
	}

}
