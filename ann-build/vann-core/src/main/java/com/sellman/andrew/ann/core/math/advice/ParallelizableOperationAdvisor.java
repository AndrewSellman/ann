package com.sellman.andrew.ann.core.math.advice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.ann.core.math.Matrix;

class ParallelizableOperationAdvisor {
	private static final int DEFAULT_TEST_COUNT = 100;
	private final int testCount;
	private final Map<AdviceKey, Boolean> parallelOpAdvice;

	public ParallelizableOperationAdvisor(final int testCount) {
		this.testCount = testCount;
		parallelOpAdvice = new ConcurrentHashMap<>();
	}

	public ParallelizableOperationAdvisor() {
		this(DEFAULT_TEST_COUNT);
	}

	protected final Boolean getAdvice(final AdviceKey key) {
		return parallelOpAdvice.get(key);
	}

	protected final void storeAdvice(final AdviceKey key, final boolean advice) {
		parallelOpAdvice.put(key, advice);
	}

	protected final int getOpTestCount() {
		return testCount;
	}

	protected final Matrix getRandomMatrix(final int rowCount, final int columnCount) {
		Matrix m = new Matrix(rowCount, columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}

		return m;
	}

}
