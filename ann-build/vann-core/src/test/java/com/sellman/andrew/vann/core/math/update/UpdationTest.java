package com.sellman.andrew.vann.core.math.update;

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
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;

public class UpdationTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });

	private Updation updation;
	private TaskService taskService;
	private UpdationByRowTaskPool opByRowTaskPool;
	private UpdationByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation5Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<UpdationByRowTask> rowTaskObjectPool = new GenericObjectPool<UpdationByRowTask>(new UpdationByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new UpdationByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<UpdationByColumnTask> columnTaskObjectPool = new GenericObjectPool<UpdationByColumnTask>(new UpdationByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new UpdationByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation5Advisor.class);

		updation = new Updation(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		updation.close();
		taskService.close();
	}

	@Test
	public void updateMatrixSequentially() {
		Matrix target = new Matrix(2, 3);
		
		updation.doOperation(M, target);
		assertResult(M, target);
		verifyAdvisor(M);
	}

	@Test
	public void updateMatrixInParallel() {
		doAsParallel(M);
		Matrix target = new Matrix(2, 3);
		
		updation.doOperation(M, target);
		assertResult(M, target);
		verifyAdvisor(M);
	}

	private void doAsParallel(Matrix m) {
		when(advisor.doAsParrallelOp(updation, m.getRowCount(), m.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix a) {
		verify(advisor).doAsParrallelOp(updation, a.getRowCount(), a.getColumnCount());
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

	private void assertResult(Matrix expected, Matrix actual) {
		for (int r = 0; r < expected.getRowCount(); r++) {
			for (int c = 0; c < expected.getColumnCount(); c++) {
				assertEquals(expected.getValue(r, c), actual.getValue(r, c), 0.0);
			}
		}
	}

}
