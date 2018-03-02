package com.sellman.andrew.ann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

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
		for (int t = 0; t < getTestCount(); t++) {
			Matrix source = getRandomMatrix(matrixRowCount, matrixColumnCount);
			Matrix target = new Matrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += getSequentialOpNanos(op, source, target);
			parallelOpTotal += getParallelOpNanos(op, source, target);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
		return asParallel;
	}

	private double getSequentialOpNanos(final AdvisableParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final Matrix source,
			final Matrix target) {
		long start = System.nanoTime();
		op.doShellSequentialOp(source, target);
		return (System.nanoTime() - start);
	}

	private long getParallelOpNanos(final AdvisableParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final Matrix source,
			final Matrix target) {
		long start = System.nanoTime();
		op.doShellParallelOp(source, target);
		return (System.nanoTime() - start);
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
