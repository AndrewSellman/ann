package com.sellman.andrew.ann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

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
		for (int t = 0; t < getTestCount(); t++) {
			Matrix m = getRandomMatrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += getSequentialOpNanos(op, m, function);
			parallelOpTotal += getParallelOpNanos(op, m, function);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / getTestCount();
		double averageParallelOp = parallelOpTotal * 1.0 / getTestCount();

		asParallel = averageParallelOp < averageSequentialOp;
		storeAdvice(key, asParallel);
		return asParallel;
	}

	private double getSequentialOpNanos(final AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final Matrix m,
			final Function f) {
		long start = System.nanoTime();
		op.doShellSequentialOp(m, f);
		return (System.nanoTime() - start);
	}

	private long getParallelOpNanos(final AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op, final Matrix m,
			final Function f) {
		long start = System.nanoTime();
		op.doShellParallelOp(m, f);
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
