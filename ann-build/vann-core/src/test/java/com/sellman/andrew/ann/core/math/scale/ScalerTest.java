package com.sellman.andrew.ann.core.math.scale;

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
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.scale.Scaler;
import com.sellman.andrew.ann.core.math.scale.ScalerByColumnTask;
import com.sellman.andrew.ann.core.math.scale.ScalerByColumnTaskFactory;
import com.sellman.andrew.ann.core.math.scale.ScalerByColumnTaskPool;
import com.sellman.andrew.ann.core.math.scale.ScalerByRowTask;
import com.sellman.andrew.ann.core.math.scale.ScalerByRowTaskFactory;
import com.sellman.andrew.ann.core.math.scale.ScalerByRowTaskPool;

public class ScalerTest {
	private static final int NO_LIMIT = -1;
	private static final int MAX_IDLE = 1000;
	private static final int MIN_IDLE = 100;
	private static final double SCALAR_VALUE = 123.45;
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
	private Scaler scaler;
	private TaskService taskService;
	private ParallelizableOperation2Advisor advisor;
	private ScalerByRowTaskPool rowTaskPool;
	private ScalerByColumnTaskPool columnTaskPool;

	private Function function = new Function() {
		@Override
		public double evaluate(double input) {
			return input + SCALAR_VALUE;
		}
	};

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().build();

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMinIdle(MIN_IDLE);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxTotal(NO_LIMIT);

		GenericObjectPool<ScalerByRowTask> rowTaskObjectPool = new GenericObjectPool<ScalerByRowTask>(new ScalerByRowTaskFactory(), poolConfig);
		rowTaskPool = new ScalerByRowTaskPool(rowTaskObjectPool);

		GenericObjectPool<ScalerByColumnTask> columnTaskObjectPool = new GenericObjectPool<ScalerByColumnTask>(new ScalerByColumnTaskFactory(), poolConfig);
		columnTaskPool = new ScalerByColumnTaskPool(columnTaskObjectPool);

		advisor = mock(ParallelizableOperation2Advisor.class);

		scaler = new Scaler(taskService, rowTaskPool, columnTaskPool, advisor);
	}

	@After
	public void completeTests() throws Exception {
		waitForTasksToBeRecycled();

		assertEquals(rowTaskPool.getBorrowedCount(), rowTaskPool.getRecycledCount());
		assertEquals(columnTaskPool.getBorrowedCount(), columnTaskPool.getRecycledCount());
		scaler.close();
		taskService.close();
	}

	@Test
	public void scale2x3MatrixSequentially() {
		Matrix result = scaler.doOperation(M2X3, function);
		assertScalerResult(M2X3, result);
		verifyAdvisor(M2X3, function);
	}

	@Test
	public void scale2x3MatrixInParallel() {
		doAsParallel(M2X3, function);

		Matrix result = scaler.doOperation(M2X3, function);
		assertScalerResult(M2X3, result);
		verifyAdvisor(M2X3, function);
	}

	@Test
	public void scale3x2MatrixSequentially() {
		Matrix result = scaler.doOperation(M3X2, function);
		assertScalerResult(M3X2, result);
		verifyAdvisor(M3X2, function);
	}

	@Test
	public void scale3x2MatrixInParallel() {
		doAsParallel(M3X2, function);

		Matrix result = scaler.doOperation(M3X2, function);
		assertScalerResult(M3X2, result);
		verifyAdvisor(M3X2, function);
	}

	private void assertScalerResult(Matrix source, Matrix target) {
		for (int r = 0; r < source.getRowCount(); r++) {
			for (int c = 0; c < source.getColumnCount(); c++) {
				assertEquals(source.getValue(r, c) + SCALAR_VALUE, target.getValue(r, c), 0.0);
			}
		}
	}

	private void doAsParallel(Matrix m, Function f) {
		when(advisor.doAsParrallelOp(scaler, m.getRowCount(), m.getColumnCount(), f)).thenReturn(true);
	}

	private void verifyAdvisor(Matrix m, Function f) {
		verify(advisor).doAsParrallelOp(scaler, m.getRowCount(), m.getColumnCount(), f);
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

}
