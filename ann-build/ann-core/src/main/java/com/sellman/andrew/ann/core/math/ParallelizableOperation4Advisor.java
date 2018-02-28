package com.sellman.andrew.ann.core.math;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class ParallelizableOperation4Advisor {

	private static final int DEFAULT_TEST_COUNT = 100;
	private final int testCount;
	private final Map<AdviceGuide, Boolean> parallelOpAdvice;

	public ParallelizableOperation4Advisor(final int testCount) {
		this.testCount = testCount;
		parallelOpAdvice = new ConcurrentHashMap<>();
	}

	public ParallelizableOperation4Advisor() {
		this(DEFAULT_TEST_COUNT);
	}

	public boolean doAsParrallelOp(final ParallelizableOperation4 op, final int matrixRowCount, final int matrixColumnCount) {
		AdviceGuide guide = new AdviceGuide(matrixRowCount, matrixColumnCount);
		Boolean asParallel = parallelOpAdvice.get(guide);
		if (asParallel != null) {
			return asParallel;
		}

		long sequentialOpTotal = 0, parallelOpTotal = 0;
		for (int t = 0; t < testCount; t++) {
			Matrix m = getRandomMatrix(matrixRowCount, matrixColumnCount);

			sequentialOpTotal += getSequentialOpNanos(op, m);
			parallelOpTotal += getParallelOpNanos(op, m);
		}

		double averageSequentialOp = sequentialOpTotal * 1.0 / testCount;
		double averageParallelOp = parallelOpTotal * 1.0 / testCount;

		asParallel = averageParallelOp < averageSequentialOp;
		parallelOpAdvice.put(guide, asParallel);
		return asParallel;
	}

	private double getSequentialOpNanos(final ParallelizableOperation4 op, final Matrix m) {
		long start = System.nanoTime();
		op.doSequentialOp(m);
		return (System.nanoTime() - start);
	}

	private long getParallelOpNanos(final ParallelizableOperation4 op, final Matrix m) {
		long start = System.nanoTime();
		op.doParallelOp(m);
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

		private AdviceGuide(int matrixRowCount, int matrixColumnCount) {
			this.matrixRowCount = matrixRowCount;
			this.matrixColumnCount = matrixColumnCount;
		}

		@Override
		public int hashCode() {
			int result = HASH_PRIME + matrixRowCount;
			result = HASH_PRIME * result + matrixColumnCount;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			AdviceGuide d = (AdviceGuide) o;
			return matrixRowCount == d.matrixRowCount && matrixColumnCount == d.matrixColumnCount;
		}
	}

}
