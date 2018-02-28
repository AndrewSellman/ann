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

public class ScalerExperimentITCase {
	private static final int MAX_ROWS = 500;
	private static final int MAX_COLUMNS = 500;
	private static final int TESTS_PER_ROUND = 100;
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 250;

	private Scaler scaler;
	private Function function;
	private TaskService taskService;
	private ScalerByRowTaskPool rowTaskPool;
	private ScalerByColumnTaskPool columnTaskPool;
	private ParallelizableOperation2Advisor advisor;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<ScalerByRowTask> rowTaskObjectPool = new GenericObjectPool<ScalerByRowTask>(new ScalerByRowTaskFactory(), poolConfig);
		rowTaskPool = new ScalerByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<ScalerByColumnTask> columnTaskobjectPool = new GenericObjectPool<ScalerByColumnTask>(new ScalerByColumnTaskFactory(), poolConfig);
		columnTaskPool = new ScalerByColumnTaskPool(columnTaskobjectPool);

		advisor = new ParallelizableOperation2Advisor();

		scaler = new Scaler(taskService, advisor, rowTaskPool, columnTaskPool);

		function = new LogisticFunction();
	}

	@After
	public void completeTest() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(rowTaskPool.getBorrowedCount(), rowTaskPool.getRecycledCount());
		assertEquals(columnTaskPool.getBorrowedCount(), columnTaskPool.getRecycledCount());
		scaler.close();
		taskService.close();
	}

	@Test
	public void sequentialScaleComparedToParallelScale() {

		for (int rowCount = 1; rowCount <= MAX_ROWS; rowCount++) {
			for (int columnCount = 1; columnCount <= MAX_COLUMNS; columnCount++) {

				Matrix m = buildMatrix(rowCount, columnCount);
				primeAdvisor(m, function);

				long totalAdvised = 0, totalSequential = 0, totalParallel = 0;
				for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {

					m = buildMatrix(rowCount, columnCount);
					totalAdvised += scale(m, function);

					m = buildMatrix(rowCount, columnCount);
					totalSequential += scaleSequential(m, function);

					m = buildMatrix(rowCount, columnCount);
					totalParallel += scaleParallel(m, function);
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

				assertTrue(advisedDifference <= 500.0);
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

	private void primeAdvisor(Matrix m, Function f) {
		advisor.doAsParrallelOp(scaler, m.getRowCount(), m.getColumnCount(), f);
	}

	private long scaleSequential(Matrix m, Function f) {
		long start = System.nanoTime();
		scaler.doSequentialOp(m, f);
		long end = System.nanoTime();
		return end - start;
	}

	private long scaleParallel(Matrix m, Function f) {
		long start = System.nanoTime();
		scaler.doParallelOp(m, f);
		long end = System.nanoTime();
		return end - start;
	}

	private long scale(Matrix m, Function f) {
		long start = System.nanoTime();
		scaler.scale(m, f);
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
