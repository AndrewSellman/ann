package com.sellman.andrew.vann.core.math.add;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.add.Addition;
import com.sellman.andrew.vann.core.math.add.AdditionByColumnTask;
import com.sellman.andrew.vann.core.math.add.AdditionByColumnTaskFactory;
import com.sellman.andrew.vann.core.math.add.AdditionByColumnTaskPool;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTask;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTaskFactory;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTaskPool;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

public class AdditionTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M2X4 = new Matrix(new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } });
	private static final Matrix M4X2 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } });

	private Addition addition;
	private TaskService taskService;
	private AdditionByRowTaskPool opByRowTaskPool;
	private AdditionByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation1Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<AdditionByRowTask> rowTaskObjectPool = new GenericObjectPool<AdditionByRowTask>(new AdditionByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new AdditionByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<AdditionByColumnTask> columnTaskObjectPool = new GenericObjectPool<AdditionByColumnTask>(new AdditionByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new AdditionByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation1Advisor.class);

		addition = new Addition(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		addition.close();
		taskService.close();
	}

	@Test
	public void add2x4MatricesInParallel() {
		doAsParallel(M2X4, M2X4);

		Matrix result = addition.doOperation(M2X4, M2X4);
		assertResult(M2X4, M2X4, result);
		verifyAdvisor(M2X4, M2X4);
		assertEquals(2, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void add2x4MatricesSequentially() {
		Matrix result = addition.doOperation(M2X4, M2X4);
		assertResult(M2X4, M2X4, result);
		verifyAdvisor(M2X4, M2X4);
		assertEquals(0, opByRowTaskPool.getBorrowedCount());
		assertEquals(0, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void add4x2MatricesInParallel() {
		doAsParallel(M4X2, M4X2);

		Matrix result = addition.doOperation(M4X2, M4X2);
		assertResult(M4X2, M4X2, result);
		verifyAdvisor(M4X2, M4X2);
		assertEquals(0, opByRowTaskPool.getBorrowedCount());
		assertEquals(2, opByColumnTaskPool.getBorrowedCount());
	}

	@Test
	public void add4x2MatricesSequentially() {
		Matrix result = addition.doOperation(M4X2, M4X2);
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
		when(advisor.doAsParrallelOp(addition, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix a, Matrix b) {
		verify(advisor).doAsParrallelOp(addition, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

	private void assertResult(Matrix a, Matrix b, Matrix result) {
		for (int rowIndex = 0; rowIndex < result.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < result.getColumnCount(); columnIndex++) {
				assertEquals(a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex), result.getValue(rowIndex, columnIndex), 0.0);
			}
		}
	}

}
