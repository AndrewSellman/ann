package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation5;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizableOperation5Test {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizableOperation5<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

	private Matrix a;
	private Matrix b;

	@Mock
	private ParallelizableOperation5Advisor advisor;

	@Mock
	private TaskService taskService;

	@Mock
	private AbstractOperationByRowTaskPool<AbstractOperationByRowTask> byRowTaskPool;

	@Mock
	private AbstractOperationByColumnTaskPool<AbstractOperationByColumnTask> byColumnTaskPool;

	@Mock
	private AbstractOperationByRowTask rowTask;

	private List<AbstractOperationByRowTask> rowTasks;

	@Before
	public void prepareTest() throws Exception {
		Whitebox.setInternalState(op, TaskService.class, taskService);
		Whitebox.setInternalState(op, AbstractOperationByRowTaskPool.class, byRowTaskPool);
		Whitebox.setInternalState(op, AbstractOperationByColumnTaskPool.class, byColumnTaskPool);
		Whitebox.setInternalState(op, ParallelizableOperation5Advisor.class, advisor);

		rowTasks = Arrays.asList(rowTask);
		doReturn(rowTasks).when(byRowTaskPool).borrow(1);

		a = new Matrix(1, 2);
		b = new Matrix(1, 2);
	}

	@Test
	public void getSequentialNanos() {
		long elapsed = op.getSequentialOpNanos(a, b);
		assertTrue(elapsed > 0);
	}

	@Test
	public void getParallelOpNanos() {
		long elapsed = op.getParallelOpNanos(a, b);
		assertTrue(elapsed > 0);
	}

	@Test
	public void doAsParallelOp() {
		doReturn(true).when(advisor).doAsParrallelOp(eq(op), eq(1), eq(2));

		boolean doInParallel = op.doAsParallelOp(a);
		assertTrue(doInParallel);
	}
}
