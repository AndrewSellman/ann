package com.sellman.andrew.ann.core.math.multiply;

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
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplication;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByColumnTask;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByColumnTaskFactory;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByRowTask;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByRowTaskFactory;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationByRowTaskPool;

public class StandardMultiplicationTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M1X2 = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private StandardMultiplication multiplier;
	private TaskService taskService;
	private StandardMultiplicationByRowTaskPool opByRowTaskPool;
	private StandardMultiplicationByColumnTaskPool opByColumnTaskPool;
	private ParallelizableOperation1Advisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<StandardMultiplicationByRowTask> rowTaskObjectPool = new GenericObjectPool<StandardMultiplicationByRowTask>(new StandardMultiplicationByRowTaskFactory(), poolConfig);
		opByRowTaskPool = new StandardMultiplicationByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<StandardMultiplicationByColumnTask> columnTaskObjectPool = new GenericObjectPool<StandardMultiplicationByColumnTask>(new StandardMultiplicationByColumnTaskFactory(), poolConfig);
		opByColumnTaskPool = new StandardMultiplicationByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation1Advisor.class);

		multiplier = new StandardMultiplication(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(opByRowTaskPool.getBorrowedCount(), opByRowTaskPool.getRecycledCount());
		assertEquals(opByColumnTaskPool.getBorrowedCount(), opByColumnTaskPool.getRecycledCount());
		multiplier.close();
		taskService.close();
	}

	@Test
	public void multiply1by2MatrixWith2by1MatrixInParallel() {
		doAsParallel(M1X2, M2X1);

		Matrix result = multiplier.doOperation(M1X2, M2X1);
		assertMultiply1by2MatrixWith2by1Matrix(result);
		verifyAdvisor(M1X2, M2X1);
	}

	@Test
	public void multiply1by2MatrixWith2by1MatrixSequentially() {
		Matrix result = multiplier.doOperation(M1X2, M2X1);
		assertMultiply1by2MatrixWith2by1Matrix(result);
		verifyAdvisor(M1X2, M2X1);
	}

	@Test
	public void multiply1by2MatrixWith2by3MatrixInParallel() {
		doAsParallel(M1X2, M2X3);

		Matrix result = multiplier.doOperation(M1X2, M2X3);
		assertMultiply1by2MatrixWith2by3Matrix(result);
		verifyAdvisor(M1X2, M2X3);
	}

	@Test
	public void multiply1by2MatrixWith2by3MatrixSequentially() {
		Matrix result = multiplier.doOperation(M1X2, M2X3);
		assertMultiply1by2MatrixWith2by3Matrix(result);
		verifyAdvisor(M1X2, M2X3);
	}

	@Test
	public void multiply2by1Matrix1With1by2MatrixinParallel() {
		doAsParallel(M2X1, M1X2);

		Matrix result = multiplier.doOperation(M2X1, M1X2);
		assertMultiply2by1Matrix1With1by2Matrix(result);
		verifyAdvisor(M2X1, M1X2);
	}

	@Test
	public void multiply2by1Matrix1With1by2MatrixSequentially() {
		Matrix result = multiplier.doOperation(M2X1, M1X2);
		assertMultiply2by1Matrix1With1by2Matrix(result);
		verifyAdvisor(M2X1, M1X2);
	}

	@Test
	public void multiply2by3MatrixWith3by2MatrixInParallel() {
		doAsParallel(M2X3, M3X2);

		Matrix result = multiplier.doOperation(M2X3, M3X2);
		assertMultiply2by3MatrixWith3by2Matrix(result);
		verifyAdvisor(M2X3, M3X2);
	}

	@Test
	public void multiply2by3MatrixWith3by2MatrixSequentially() {
		Matrix result = multiplier.doOperation(M2X3, M3X2);
		assertMultiply2by3MatrixWith3by2Matrix(result);
		verifyAdvisor(M2X3, M3X2);
	}

	@Test
	public void multiply3by2MatrixWith2by3MatrixInParallel() {
		doAsParallel(M3X2, M2X3);

		Matrix result = multiplier.doOperation(M3X2, M2X3);
		assertMultiply3by2MatrixWith2by3Matrix(result);
		verifyAdvisor(M3X2, M2X3);
	}

	@Test
	public void multiply3by2MatrixWith2by3MatrixSequentially() {
		Matrix result = multiplier.doOperation(M3X2, M2X3);
		assertMultiply3by2MatrixWith2by3Matrix(result);
		verifyAdvisor(M3X2, M2X3);
	}

	private void assertMultiply1by2MatrixWith2by1Matrix(Matrix result) {
		assertEquals(1, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(11.0, result.getValue(0, 0), 0.0);
	}

	private void assertMultiply1by2MatrixWith2by3Matrix(Matrix result) {
		assertEquals(1, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(9.0, result.getValue(0, 0), 0.0);
		assertEquals(12.0, result.getValue(0, 1), 0.0);
		assertEquals(15.0, result.getValue(0, 2), 0.0);
	}

	private void assertMultiply2by1Matrix1With1by2Matrix(Matrix result) {
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(3.0, result.getValue(0, 0), 0.0);
		assertEquals(6.0, result.getValue(0, 1), 0.0);
		assertEquals(4.0, result.getValue(1, 0), 0.0);
		assertEquals(8.0, result.getValue(1, 1), 0.0);
	}

	private void assertMultiply2by3MatrixWith3by2Matrix(Matrix result) {
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(58.0, result.getValue(0, 0), 0.0);
		assertEquals(64.0, result.getValue(0, 1), 0.0);
		assertEquals(139.0, result.getValue(1, 0), 0.0);
		assertEquals(154.0, result.getValue(1, 1), 0.0);
	}

	public void assertMultiply3by2MatrixWith2by3Matrix(Matrix result) {
		assertEquals(3, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(39.0, result.getValue(0, 0), 0.0);
		assertEquals(54.0, result.getValue(0, 1), 0.0);
		assertEquals(69.0, result.getValue(0, 2), 0.0);
		assertEquals(49.0, result.getValue(1, 0), 0.0);
		assertEquals(68.0, result.getValue(1, 1), 0.0);
		assertEquals(87.0, result.getValue(1, 2), 0.0);
		assertEquals(59.0, result.getValue(2, 0), 0.0);
		assertEquals(82.0, result.getValue(2, 1), 0.0);
		assertEquals(105.0, result.getValue(2, 2), 0.0);
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

	private void doAsParallel(Matrix left, Matrix right) {
		when(advisor.doAsParrallelOp(multiplier, left.getRowCount(), left.getColumnCount(), right.getRowCount(), right.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix left, Matrix right) {
		verify(advisor).doAsParrallelOp(multiplier, left.getRowCount(), left.getColumnCount(), right.getRowCount(), right.getColumnCount());
	}

}
