package com.sellman.andrew.ann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.vann.core.cache.Cache;

class ParallelizableOperationAdvisor {
	private static final int DEFAULT_TEST_COUNT = 100;
	private final int testCount;
	private final Cache<AdviceKey, Boolean> cache;

	public ParallelizableOperationAdvisor(final int testCount, Cache<AdviceKey, Boolean> cache) {
		this.testCount = testCount;
		this.cache = cache;
	}

	public ParallelizableOperationAdvisor(Cache<AdviceKey, Boolean> cache) {
		this(DEFAULT_TEST_COUNT, cache);
	}

	protected final Boolean getAdvice(final AdviceKey key) {
		return cache.retrieve(key);
	}

	protected final void storeAdvice(final AdviceKey key, final boolean advice) {
		cache.store(key, advice);
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
