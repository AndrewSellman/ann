package com.sellman.andrew.vann.core.math.transpose;

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
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.vann.core.math.transpose.Transposition;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByColumnTask;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByColumnTaskFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByColumnTaskPool;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByRowTask;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByRowTaskFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionByRowTaskPool;

public class TranspositionTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M1X1 = new Matrix(new double[][] { { 1 } });
	private static final Matrix M1X2 = new Matrix(new double[][] { { 3, 4 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix M2X2A = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
	private static final Matrix M2X2B = new Matrix(new double[][] { { 1, 3 }, { 2, 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 1, 4 }, { 2, 5 }, { 3, 6 } });

	private Transposition transposition;
	private TaskService taskService;
	private TranspositionByRowTaskPool opByRowTaskPool;
	private TranspositionByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation3Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<TranspositionByRowTask> rowTaskObjectPool = new GenericObjectPool<TranspositionByRowTask>(new TranspositionByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new TranspositionByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<TranspositionByColumnTask> columnTaskObjectPool = new GenericObjectPool<TranspositionByColumnTask>(new TranspositionByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new TranspositionByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation3Advisor.class);

		transposition = new Transposition(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		transposition.close();
		taskService.close();
	}

	@Test
	public void transpose3by2atrixSequentially() {
		Matrix result = transposition.doOperation(M3X2);
		assertResult(M2X3, result);
		verifyAdvisor(M3X2);
	}

	@Test
	public void transpose3by2atrixInParallel() {
		doAsParallel(M3X2);

		Matrix result = transposition.doOperation(M3X2);
		assertResult(M2X3, result);
		verifyAdvisor(M3X2);
	}

	@Test
	public void transpose2by3atrixSequentially() {
		Matrix result = transposition.doOperation(M2X3);
		assertResult(M3X2, result);
		verifyAdvisor(M2X3);
	}

	@Test
	public void transpose2by3atrixInParallel() {
		doAsParallel(M2X3);

		Matrix result = transposition.doOperation(M2X3);
		assertResult(M3X2, result);
		verifyAdvisor(M2X3);
	}

	@Test
	public void transpose2by2MatrixSequentially() {
		Matrix result = transposition.doOperation(M2X2A);
		assertResult(M2X2B, result);
		verifyAdvisor(M2X2A);
	}

	@Test
	public void transpose2by2MatrixInParallel() {
		doAsParallel(M2X2A);

		Matrix result = transposition.doOperation(M2X2A);
		assertResult(M2X2B, result);
		verifyAdvisor(M2X2A);
	}

	@Test
	public void transpose2by1MatrixSequentially() {
		Matrix result = transposition.doOperation(M2X1);
		assertResult(M1X2, result);
		verifyAdvisor(M2X1);
	}

	@Test
	public void transpose2by1MatrixInParallel() {
		doAsParallel(M2X1);

		Matrix result = transposition.doOperation(M2X1);
		assertResult(M1X2, result);
		verifyAdvisor(M2X1);
	}

	@Test
	public void transpose1by2MatrixSequentially() {
		Matrix result = transposition.doOperation(M1X2);
		assertResult(M2X1, result);
		verifyAdvisor(M1X2);
	}

	@Test
	public void transpose1by2MatrixInParallel() {
		doAsParallel(M1X2);

		Matrix result = transposition.doOperation(M1X2);
		assertResult(M2X1, result);
		verifyAdvisor(M1X2);
	}

	@Test
	public void transpose1by1MatrixSequentially() {
		Matrix result = transposition.doOperation(M1X1);
		assertResult(M1X1, result);
		verifyAdvisor(M1X1);
	}

	@Test
	public void transpose1by1MatrixInParallel() {
		doAsParallel(M1X1);

		Matrix result = transposition.doOperation(M1X1);
		assertResult(M1X1, result);
		verifyAdvisor(M1X1);
	}

	private void doAsParallel(Matrix m) {
		when(advisor.doAsParrallelOp(transposition, m.getRowCount(), m.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix a) {
		verify(advisor).doAsParrallelOp(transposition, a.getRowCount(), a.getColumnCount());
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
		assertEquals(expected.getRowCount(), actual.getRowCount());
		assertEquals(expected.getColumnCount(), actual.getColumnCount());
		for (int r = 0; r < expected.getRowCount(); r++) {
			for (int c = 0; c < expected.getColumnCount(); c++) {
				assertEquals(expected.getValue(r, c), actual.getValue(r, c), 0.0);
			}
		}
	}

}
