package com.sellman.andrew.ann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public class ParallelizableOperation3Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation3Advisor(final int testCount) {
		super(testCount);
	}

	public ParallelizableOperation3Advisor() {
		super();
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixRowCount, final int matrixColumnCount) {
		AdviceKeyC guide = new AdviceKeyC(matrixRowCount, matrixColumnCount);
		Boolean asParallel = getAdvice(guide);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < getTestCount(); t++) {
			Matrix m = getRandomMatrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += op.getSequentialOpNanos(m);
			parallelOpTotal += op.getParallelOpNanos(m);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(guide, asParallel);
		return asParallel;
	}

	private Matrix getRandomMatrix(final int rowCount, final int columnCount) {
		Matrix m = new Matrix(rowCount, columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}

		return m;
	}

}