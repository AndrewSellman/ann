package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixMultiplierExperimentITCase {
	private static final int MAX_DIMNESION = 1000;
	private static final int TESTS_PER_ROUND = 100;

	private Multiplier multiplier;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();
		multiplier = new Multiplier(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void sequentialMultiplicationComparedToParallelMultiplication() {
		for (int d = 300; d <= MAX_DIMNESION; d++) {

			long fastestSequential = Long.MAX_VALUE, worstSequential = Long.MIN_VALUE, totalSequential = 0;
			long fastestParallel = Long.MAX_VALUE, worstParallel = Long.MIN_VALUE, totalParallel = 0;

			for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {
				Matrix left = buildMatrix(d);
				Matrix right = buildMatrix(d);

				long sequentialTest = multiplySequential(left, right);
				totalSequential += sequentialTest;
				if (totalSequential > worstSequential) {
					worstSequential = sequentialTest;
				}
				if (totalSequential < fastestSequential) {
					fastestSequential = sequentialTest;
				}

				long parallelTest = multiplyParallel(left, right);
				totalParallel += parallelTest;
				if (parallelTest > worstParallel) {
					worstParallel = parallelTest;
				}
				if (parallelTest < fastestParallel) {
					fastestParallel = parallelTest;
				}
			}

			double avererageSequential = totalSequential / TESTS_PER_ROUND;
			double avererageParallel = totalParallel / TESTS_PER_ROUND;
			String better = "SEQ";
			if (avererageParallel < avererageSequential) {
				better = "PAR";
			}

			System.out.println(d + " " + avererageSequential + " " + avererageParallel + " " + (avererageParallel - avererageSequential) + " " + better);
		}
	}

	private long multiplySequential(Matrix left, Matrix right) {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(Long.MAX_VALUE);
		return multiply(left, right);
	}

	private long multiplyParallel(Matrix left, Matrix right) {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);
		return multiply(left, right);
	}

	private long multiply(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.multiply(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private Matrix buildMatrix(int dimension) {
		Matrix m = new Matrix(dimension, dimension);
		for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
			for (int columnIndex = 0; columnIndex < dimension; columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}
		return m;
	}

}
