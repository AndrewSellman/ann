package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.AdviceKeyB;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation2;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation2AdvisorTest {
	private static final int TEST_COUNT = 10;
	private static final int MATRIX_A_ROWS = 1;
	private static final int MATRIX_A_COLUMNS = 2;
	private static final long OP_RUNNING_TIME = 1000;

	private ParallelizableOperation2Advisor advisor;
	private AdviceKey adviceKey;
	private Cache<AdviceKey, Boolean> cache;

	@Mock
	private Function f;

	@Mock
	private AdvisableParallelizableOperation2<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

	@Before
	public void prepareTest() throws Exception {
		cache = new CacheBuilder<AdviceKey, Boolean>("op2").build();
		cache.clear();
		advisor = new ParallelizableOperation2Advisor(TEST_COUNT, cache);
		adviceKey = new AdviceKeyB(MATRIX_A_ROWS, MATRIX_A_COLUMNS, f);
	}

	@Test
	public void doAsParallelWhenOperationIsFasterWhenRanInParallel() {
		doReturn(OP_RUNNING_TIME + 10).when(op).getSequentialOpNanos(any(Matrix.class), eq(f));
		doReturn(OP_RUNNING_TIME).when(op).getParallelOpNanos(any(Matrix.class), eq(f));

		boolean doOpInParallel = advisor.doAsParrallelOp(op, MATRIX_A_ROWS, MATRIX_A_COLUMNS, f);
		assertTrue(doOpInParallel);
		verify(op, times(TEST_COUNT)).getSequentialOpNanos(any(Matrix.class), eq(f));
		verify(op, times(TEST_COUNT)).getParallelOpNanos(any(Matrix.class), eq(f));
		assertTrue(advisor.getAdvice(adviceKey));
	}

	@Test
	public void doAsParallelWhenOperationIsFasterWhenRanSequentially() {
		doReturn(OP_RUNNING_TIME).when(op).getSequentialOpNanos(any(Matrix.class), eq(f));
		doReturn(OP_RUNNING_TIME + 10).when(op).getParallelOpNanos(any(Matrix.class), eq(f));

		boolean doOpInParallel = advisor.doAsParrallelOp(op, MATRIX_A_ROWS, MATRIX_A_COLUMNS, f);
		assertFalse(doOpInParallel);
		verify(op, times(TEST_COUNT)).getSequentialOpNanos(any(Matrix.class), eq(f));
		verify(op, times(TEST_COUNT)).getParallelOpNanos(any(Matrix.class), eq(f));
		assertFalse(advisor.getAdvice(adviceKey));
	}

}
