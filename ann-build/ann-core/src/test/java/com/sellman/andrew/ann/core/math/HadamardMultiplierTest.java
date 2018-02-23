package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import static org.mockito.Mockito.*;

public class HadamardMultiplierTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final Matrix M1X2 = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private HadamardMultiplier multiplier;
	private TaskService taskService;
	private HadamardMultiplyByRowTaskPool rowTaskPool;
	private HadamardMultiplyByColumnTaskPool columnTaskPool;
	private ParallelizableOperationAdvisor advisor;

	@Before
	public void prepareTests() {
		taskService = new TaskServiceBuilder().highPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<HadamardMultiplyByRowTask> rowTaskObjectPool = new GenericObjectPool<HadamardMultiplyByRowTask>(new HadamardMultiplyByRowTaskFactory(), poolConfig);
		rowTaskPool = new HadamardMultiplyByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<HadamardMultiplyByColumnTask> columnTaskObjectPool = new GenericObjectPool<HadamardMultiplyByColumnTask>(new HadamardMultiplyByColumnTaskFactory(), poolConfig);
		columnTaskPool = new HadamardMultiplyByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperationAdvisor.class);

		multiplier = new HadamardMultiplier(taskService, rowTaskPool, columnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(rowTaskPool.getBorrowedCount(), rowTaskPool.getRecycledCount());
		assertEquals(columnTaskPool.getBorrowedCount(), columnTaskPool.getRecycledCount());
		multiplier.close();
		taskService.close();
	}

	@Test
	public void multiply1by2MatrixWith1by2MatrixParallel() {
		doAsParallel(M1X2, M1X2);

		Matrix result = multiplier.multiply(M1X2, M1X2);
		assertTarget(M1X2, M1X2, result);
		verifyAdvisor(M1X2, M1X2);
	}

	@Test
	public void multiply1by2MatrixWith1by2MatrixSequential() {
		Matrix result = multiplier.multiply(M1X2, M1X2);
		assertTarget(M1X2, M1X2, result);
		verifyAdvisor(M1X2, M1X2);
	}

	@Test
	public void multiply2by1MatrixWith2by1MatrixParallel() {
		doAsParallel(M2X1, M2X1);

		Matrix result = multiplier.multiply(M2X1, M2X1);
		assertTarget(M2X1, M2X1, result);
		verifyAdvisor(M2X1, M2X1);
	}

	@Test
	public void multiply2by1MatrixWith2by1MatrixSequential() {
		Matrix result = multiplier.multiply(M2X1, M2X1);
		assertTarget(M2X1, M2X1, result);
		verifyAdvisor(M2X1, M2X1);
	}

	@Test
	public void multiply2by3MatrixWith2by3MatrixParallel() {
		doAsParallel(M2X3, M2X3);
		
		Matrix result = multiplier.multiply(M2X3, M2X3);
		assertTarget(M2X3, M2X3, result);
		verifyAdvisor(M2X3, M2X3);
	}

	@Test
	public void multiply2by3MatrixWith2by3MatrixSequential() {
		Matrix result = multiplier.multiply(M2X3, M2X3);
		assertTarget(M2X3, M2X3, result);
		verifyAdvisor(M2X3, M2X3);
	}
	
	@Test
	public void multiply3by2MatrixWith3by2MatrixParallel() {
		doAsParallel(M3X2, M3X2);
		
		Matrix result = multiplier.multiply(M3X2, M3X2);
		assertTarget(M3X2, M3X2, result);
		verifyAdvisor(M3X2, M3X2);
	}

	@Test
	public void multiply3by2MatrixWith3by2MatrixSequential() {
		Matrix result = multiplier.multiply(M3X2, M3X2);
		assertTarget(M3X2, M3X2, result);
		verifyAdvisor(M3X2, M3X2);
	}

	private void assertTarget(Matrix a, Matrix b, Matrix target) {
		for (int rowIndex = 0; rowIndex < target.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < target.getColumnCount(); columnIndex++) {
				assertEquals(a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex), target.getValue(rowIndex, columnIndex), 0.0);
			}
		}
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

	private void doAsParallel(Matrix a, Matrix b) {
		when(advisor.doAsParrallelOp(multiplier, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount())).thenReturn(true);
	}

	private void verifyAdvisor(Matrix a, Matrix b) {
		verify(advisor).doAsParrallelOp(multiplier, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

}
