package com.sellman.andrew.ann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public class ParallelizableOperation1Advisor extends ParallelizableOperationAdvisor {

	public ParallelizableOperation1Advisor(final int testCount) {
		super(testCount);
	}

	public ParallelizableOperation1Advisor() {
		super();
	}

	public boolean doAsParrallelOp(final AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final int matrixARowCount, final int matrixAColumnCount, final int matrixBRowCount, final int matrixBColumnCount) {
		AdviceKeyA key = new AdviceKeyA(matrixARowCount, matrixAColumnCount, matrixBRowCount, matrixBColumnCount);
		Boolean asParallel = getAdvice(key);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < getTestCount(); t++) {
			Matrix a = getRandomMatrix(matrixARowCount, matrixAColumnCount);
			Matrix b = getRandomMatrix(matrixBRowCount, matrixBColumnCount);

			sequentialOpTotal += op.getSequentialOpNanos(a, b);
			parallelOpTotal += op.getParallelOpNanos(a, b);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
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
