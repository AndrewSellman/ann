package com.sellman.andrew.ann.core.math;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class ParallelizableOperation2Advisor {
	private static final int DEFAULT_TEST_COUNT = 100;
	private final int testCount;
	private final Map<AdviceGuide, Boolean> parallelOpAdvice;

	public ParallelizableOperation2Advisor(final int testCount) {
		this.testCount = testCount;
		parallelOpAdvice = new ConcurrentHashMap<>();
	}

	public ParallelizableOperation2Advisor() {
		this(DEFAULT_TEST_COUNT);
	}

	public boolean doAsParrallelOp(final ParallelizableOperation2 op, final int matrixRowCount, final int matrixColumnCount, final Function function) {
		AdviceGuide guide = new AdviceGuide(matrixRowCount, matrixColumnCount, function);
		Boolean asParallel = parallelOpAdvice.get(guide);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < testCount; t++) {
			Matrix m = getRandomMatrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += getSequentialOpNanos(op, m, function);
			parallelOpTotal += getParallelOpNanos(op, m, function);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / testCount;
		double averageParallelOp = parallelOpTotal * 1.0 / testCount;

		asParallel = averageParallelOp < averageSequentialOp;
		parallelOpAdvice.put(guide, asParallel);
		return asParallel;
	}

	private double getSequentialOpNanos(final ParallelizableOperation2 op, final Matrix m, final Function f) {
		long start = System.nanoTime();
		op.doSequentialOp(m, f);
		return (System.nanoTime() - start);
	}

	private long getParallelOpNanos(final ParallelizableOperation2 op, final Matrix m, final Function f) {
		long start = System.nanoTime();
		op.doParallelOp(m, f);
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

	private class AdviceGuide {
		private static final int HASH_PRIME = 31;
		private final int matrixRowCount;
		private final int matrixColumnCount;
		private final int functionHashCode;

		private AdviceGuide(int matrixRowCount, int matrixColumnCount, Function function) {
			this.matrixRowCount = matrixRowCount;
			this.matrixColumnCount = matrixColumnCount;
			functionHashCode = function.hashCode();
		}

		@Override
		public int hashCode() {
			int result = HASH_PRIME + matrixRowCount;
			result = HASH_PRIME * result + matrixColumnCount;
			result = HASH_PRIME * result + functionHashCode;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			AdviceGuide d = (AdviceGuide) o;
			return matrixRowCount == d.matrixRowCount && matrixColumnCount == d.matrixColumnCount && functionHashCode == d.functionHashCode;
		}
	}

}
