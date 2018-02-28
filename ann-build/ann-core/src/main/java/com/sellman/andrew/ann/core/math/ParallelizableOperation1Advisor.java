package com.sellman.andrew.ann.core.math;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class ParallelizableOperation1Advisor {
	private static final int DEFAULT_TEST_COUNT = 100;
	private final int testCount;
	private final Map<AdviceGuide, Boolean> parallelOpAdvice;

	public ParallelizableOperation1Advisor(final int testCount) {
		this.testCount = testCount;
		parallelOpAdvice = new ConcurrentHashMap<>();
	}

	public ParallelizableOperation1Advisor() {
		this(DEFAULT_TEST_COUNT);
	}

	public boolean doAsParrallelOp(final ParallelizableOperation1 op, final int matrixARowCount, final int matrixAColumnCount, final int matrixBRowCount, final int matrixBColumnCount) {
		AdviceGuide guide = new AdviceGuide(matrixARowCount, matrixAColumnCount, matrixBRowCount, matrixBColumnCount);
		Boolean asParallel = parallelOpAdvice.get(guide);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < testCount; t++) {
			Matrix a = getRandomMatrix(matrixARowCount, matrixAColumnCount);
			Matrix b = getRandomMatrix(matrixBRowCount, matrixBColumnCount);

			sequentialOpTotal += getSequentialOpNanos(op, a, b);
			parallelOpTotal += getParallelOpNanos(op, a, b);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / testCount;
		double averageParallelOp = parallelOpTotal * 1.0 / testCount;

		asParallel = averageParallelOp < averageSequentialOp;
		parallelOpAdvice.put(guide, asParallel);
		return asParallel;
	}

	private double getSequentialOpNanos(final ParallelizableOperation1 op, final Matrix a, final Matrix b) {
		long start = System.nanoTime();
		op.doSequentialOp(a, b);
		return (System.nanoTime() - start);
	}

	private long getParallelOpNanos(final ParallelizableOperation1 op, final Matrix a, final Matrix b) {
		long start = System.nanoTime();
		op.doParallelOp(a, b);
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
		private final int matrixARowCount;
		private final int matrixAColumnCount;
		private final int matrixBRowCount;
		private final int matrixBColumnCount;

		private AdviceGuide(int matrixARowCount, int matrixAColumnCount, int matrixBRowCount, int matrixBColumnCount) {
			this.matrixARowCount = matrixARowCount;
			this.matrixAColumnCount = matrixAColumnCount;
			this.matrixBRowCount = matrixBRowCount;
			this.matrixBColumnCount = matrixBColumnCount;
		}

		@Override
		public int hashCode() {
			int result = HASH_PRIME + matrixARowCount;
			result = HASH_PRIME * result + matrixAColumnCount;
			result = HASH_PRIME * result + matrixBRowCount;
			result = HASH_PRIME * result + matrixBColumnCount;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			AdviceGuide d = (AdviceGuide) o;
			return matrixARowCount == d.matrixARowCount && matrixAColumnCount == d.matrixAColumnCount && matrixBRowCount == d.matrixBRowCount && matrixBColumnCount == d.matrixBColumnCount;
		}
	}

}
