package com.sellman.andrew.ann.core.math.sum;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;

public class SummationTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });

	private Summation summation;
	private TaskService taskService;
	private SummationByRowTaskPool opByRowTaskPool;
	private SummationByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation4Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<SummationByRowTask> rowTaskObjectPool = new GenericObjectPool<SummationByRowTask>(new SummationByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new SummationByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<SummationByColumnTask> columnTaskObjectPool = new GenericObjectPool<SummationByColumnTask>(new SummationByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new SummationByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation4Advisor.class);

		summation = new Summation(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		summation.close();
		taskService.close();
	}

	@Test
	public void sumInParallel() {
		doAsParallel(M);

		double result = summation.doOperation(M);
		assertResult(M, result);
		verifyAdvisor(M);
		assertEquals(2, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void sumSequentially() {
		double result = summation.doOperation(M);
		assertResult(M, result);
		verifyAdvisor(M);
		assertEquals(0, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	private void waitForTasksToBeRecycled() throws InterruptedException {
		int attempt = 0;
		while (opByRowTaskPool.getBorrowedCount() != opByRowTaskPool.getRecycledCount() || opByColumnTaskPool.getBorrowedCount() != opByColumnTaskPool.getRecycledCount()) {
			Thread.sleep(100);
			if (attempt > 5) {
				break;
			}
			attempt++;
		}
	}

	private void doAsParallel(Matrix m) {
		when(advisor.doAsParrallelOp(summation, m.getRowCount(), m.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix m) {
		verify(advisor).doAsParrallelOp(summation, m.getRowCount(), m.getColumnCount());
	}

	private void assertResult(Matrix m, double actualResult) {
		double expectedResult = 0;
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				expectedResult += m.getValue(rowIndex, columnIndex);
			}
		}

		assertEquals(expectedResult, actualResult, 0.0);
	}

}
