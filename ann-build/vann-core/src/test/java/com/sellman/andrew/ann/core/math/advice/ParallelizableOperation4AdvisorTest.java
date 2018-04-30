package com.sellman.andrew.ann.core.math.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation4AdvisorTest {
	private static final int TEST_COUNT = 10;
	private static final int MATRIX_A_ROWS = 1;
	private static final int MATRIX_A_COLUMNS = 2;
	private static final long OP_RUNNING_TIME = 1000;

	private ParallelizableOperation4Advisor advisor;
	private AdviceKey adviceKey;
	private Cache<AdviceKey, Boolean> cache;

	@Mock
	private AdvisableParallelizableOperation4<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

	@Before
	public void prepareTest() throws Exception {
		cache = new CacheBuilder<AdviceKey, Boolean>("op4").build();
		cache.clear();
		advisor = new ParallelizableOperation4Advisor(TEST_COUNT, cache);
		adviceKey = new AdviceKeyC(MATRIX_A_ROWS, MATRIX_A_COLUMNS);
	}

	@Test
	public void doAsParallelWhenOperationIsFasterWhenRanInParallel() {
		doReturn(OP_RUNNING_TIME + 10).when(op).getSequentialOpNanos(any(Matrix.class));
		doReturn(OP_RUNNING_TIME).when(op).getParallelOpNanos(any(Matrix.class));

		boolean doOpInParallel = advisor.doAsParrallelOp(op, MATRIX_A_ROWS, MATRIX_A_COLUMNS);
		assertTrue(doOpInParallel);
		verify(op, times(TEST_COUNT)).getSequentialOpNanos(any(Matrix.class));
		verify(op, times(TEST_COUNT)).getParallelOpNanos(any(Matrix.class));
		assertTrue(advisor.getAdvice(adviceKey));
	}

	@Test
	public void doAsParallelWhenOperationIsFasterWhenRanSequentially() {
		doReturn(OP_RUNNING_TIME).when(op).getSequentialOpNanos(any(Matrix.class));
		doReturn(OP_RUNNING_TIME + 10).when(op).getParallelOpNanos(any(Matrix.class));

		boolean doOpInParallel = advisor.doAsParrallelOp(op, MATRIX_A_ROWS, MATRIX_A_COLUMNS);
		assertFalse(doOpInParallel);
		verify(op, times(TEST_COUNT)).getSequentialOpNanos(any(Matrix.class));
		verify(op, times(TEST_COUNT)).getParallelOpNanos(any(Matrix.class));
		assertFalse(advisor.getAdvice(adviceKey));
	}

}
