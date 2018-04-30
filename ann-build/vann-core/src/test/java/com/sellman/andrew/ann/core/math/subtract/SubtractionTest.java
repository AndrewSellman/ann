package com.sellman.andrew.ann.core.math.subtract;

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
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;

public class SubtractionTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M2X4 = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });
	private static final Matrix M4X2 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } });

	private Subtraction subtraction;
	private TaskService taskService;
	private SubtractionByRowTaskPool opByRowTaskPool;
	private SubtractionByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation1Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<SubtractionByRowTask> rowTaskObjectPool = new GenericObjectPool<SubtractionByRowTask>(new SubtractionByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new SubtractionByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<SubtractionByColumnTask> columnTaskObjectPool = new GenericObjectPool<SubtractionByColumnTask>(new SubtractionByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new SubtractionByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation1Advisor.class);

		subtraction = new Subtraction(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		subtraction.close();
		taskService.close();
	}

	@Test
	public void subtract2x4MatricesInParallel() {
		doAsParallel(M2X4, M2X4);

		Matrix result = subtraction.doOperation(M2X4, M2X4);
		assertResult(M2X4, M2X4, result);
		verifyAdvisor(M2X4, M2X4);
		assertEquals(2, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void subtract2x4MatricesSequentially() {
		Matrix result = subtraction.doOperation(M2X4, M2X4);
		assertResult(M2X4, M2X4, result);
		verifyAdvisor(M2X4, M2X4);
		assertEquals(0, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void subtract4x2MatricesInParallel() {
		doAsParallel(M4X2, M4X2);

		Matrix result = subtraction.doOperation(M4X2, M4X2);
		assertResult(M4X2, M4X2, result);
		verifyAdvisor(M4X2, M4X2);
		assertEquals(0, opByRowTaskPool.getBorrowedCount());
		assertEquals(2, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void subtract4x2MatricesSequentially() {
		Matrix result = subtraction.doOperation(M4X2, M4X2);
		assertResult(M4X2, M4X2, result);
		verifyAdvisor(M4X2, M4X2);
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

	private void doAsParallel(Matrix a, Matrix b) {
		when(advisor.doAsParrallelOp(subtraction, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix a, Matrix b) {
		verify(advisor).doAsParrallelOp(subtraction, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

	private void assertResult(Matrix a, Matrix b, Matrix result) {
		for (int rowIndex = 0; rowIndex < result.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < result.getColumnCount(); columnIndex++) {
				assertEquals(a.getValue(rowIndex, columnIndex) - b.getValue(rowIndex, columnIndex), result.getValue(rowIndex, columnIndex), 0.0);
			}
		}
	}

}
