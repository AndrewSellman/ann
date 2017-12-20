package com.sellman.andrew.ann.core.math;

import java.util.concurrent.atomic.AtomicLong;

class ParallelTaskGate {
	private static final AtomicLong PARALLEL_MATRIX_TASKS_THRESHOLD;
	static {
		if (Runtime.getRuntime().availableProcessors() == 1) {
			PARALLEL_MATRIX_TASKS_THRESHOLD = new AtomicLong(Long.MAX_VALUE);
		} else {
			PARALLEL_MATRIX_TASKS_THRESHOLD = new AtomicLong(315 * 315);
		}
	}

	public static boolean doMatrixTasksInParrallel(long matrixCellCount) {
		return matrixCellCount >= PARALLEL_MATRIX_TASKS_THRESHOLD.get();
	}

	public static void setParrallelMatrixTasksCellCountThreshold(long threshold) {
		PARALLEL_MATRIX_TASKS_THRESHOLD.set(threshold);
	}

}
