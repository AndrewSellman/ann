package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class StandardMultiplierExperimentITCase {
	private static final int MAX_ROWS = 500;
	private static final int MAX_COLUMNS = 500;
	private static final int TESTS_PER_ROUND = 100;
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 500;

	private StandardMultiplier multiplier;
	private TaskService taskService;
	private StandardMultiplyByRowTaskPool rowTaskPool;
	private StandardMultiplyByColumnTaskPool columnTaskPool;
	private ParallelizableOperationAdvisor advisor;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<StandardMultiplyByRowTask> rowTaskObjectPool = new GenericObjectPool<StandardMultiplyByRowTask>(new StandardMultiplyByRowTaskFactory(), poolConfig);
		rowTaskPool = new StandardMultiplyByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<StandardMultiplyByColumnTask> columnTaskobjectPool = new GenericObjectPool<StandardMultiplyByColumnTask>(new StandardMultiplyByColumnTaskFactory(), poolConfig);
		columnTaskPool = new StandardMultiplyByColumnTaskPool(columnTaskobjectPool);

		advisor = new ParallelizableOperationAdvisor();

		multiplier = new StandardMultiplier(taskService, rowTaskPool, columnTaskPool, advisor);
	}

	@After
	public void completeTest() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(rowTaskPool.getBorrowedCount(), rowTaskPool.getRecycledCount());
		assertEquals(columnTaskPool.getBorrowedCount(), columnTaskPool.getRecycledCount());
		multiplier.close();
		taskService.close();
	}

	@Test
	public void sequentialMultiplicationComparedToParallelMultiplication() {

		for (int rowCount = 1; rowCount <= MAX_ROWS; rowCount++) {
			for (int columnCount = 1; columnCount <= MAX_COLUMNS; columnCount++) {

				Matrix left = buildMatrix(rowCount, columnCount);
				Matrix right = buildMatrix(columnCount, rowCount);
				primeAdvisor(left, right);

				long totalAdvised = 0, totalSequential = 0, totalParallel = 0;
				for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalAdvised += multiply(left, right);

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalSequential += multiplySequential(left, right);

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalParallel += multiplyParallel(left, right);
				}

				double avererageSequential = totalSequential * 1.0 / TESTS_PER_ROUND / 1000;
				double avererageParallel = totalParallel * 1.0 / TESTS_PER_ROUND / 1000;
				double averageAdvised = totalAdvised * 1.0 / TESTS_PER_ROUND / 1000;
				String best = "SEQ";
				double advisedDifference = averageAdvised - avererageSequential;
				if (avererageParallel < avererageSequential) {
					best = "PAR";
					advisedDifference = averageAdvised - avererageParallel;
				}

				if (columnCount % 25 == 0) {
					headers();
				}

				StringBuilder sb = new StringBuilder();
				sb.append(formatRowCount(rowCount));
				sb.append(" ");
				sb.append(formatColumnCount(columnCount));
				sb.append("  ");
				sb.append(best);
				sb.append(format(avererageSequential));
				sb.append(format(avererageParallel));
				sb.append(format(averageAdvised));
				sb.append(format(advisedDifference));
				System.out.println(sb.toString());

				assertTrue(advisedDifference <= 250.0);
			}
		}
	}

	private void headers() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append(lpad("rows", formatRowCount(0).length()));
		sb.append(" ");
		sb.append(lpad("cols", formatColumnCount(0).length()));
		sb.append(" ");
		sb.append("best");
		sb.append("  avg seq");
		sb.append("  avg par");
		sb.append("  avg adv");
		sb.append("  adv dif");
		System.out.println(sb.toString());

	}

	private void primeAdvisor(Matrix left, Matrix right) {
		advisor.doAsParrallelOp(multiplier, left.getRowCount(), left.getColumnCount(), right.getRowCount(), right.getColumnCount());
	}

	private long multiplySequential(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.doSequentialOp(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private long multiplyParallel(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.doParallelOp(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private long multiply(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.multiply(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private Matrix buildMatrix(int rowCount, int columnCount) {
		Matrix m = new Matrix(rowCount, columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}
		return m;
	}

	private void waitForTasksToBeRecycled() throws InterruptedException {
		int attempt = 0;
		while (rowTaskPool.getBorrowedCount() != rowTaskPool.getRecycledCount() || columnTaskPool.getBorrowedCount() != columnTaskPool.getRecycledCount()) {
			Thread.sleep(100);
			if (attempt > 5) {
				break;
			}
			attempt++;
		}
	}

	private String format(double d) {
		return String.format("% 9.3f", d);
	}

	private String formatColumnCount(int columnCount) {
		return String.format("%5d", columnCount);
	}

	private String formatRowCount(int rowCount) {
		return String.format("%5d", rowCount);
	}

	private String lpad(String s, int size) {
		while (s.length() < size) {
			s = " " + s;
		}

		return s;
	}

}
