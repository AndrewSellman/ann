package com.sellman.andrew.ann.core.math.advice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	protected Boolean getAdvice(AdviceKey key) {
		return parallelOpAdvice.get(key);
	}

	protected void storeAdvice(AdviceKey key, boolean advice) {
		parallelOpAdvice.put(key, advice);
	}

	protected int getTestCount() {
		return testCount;
	}
	
}
