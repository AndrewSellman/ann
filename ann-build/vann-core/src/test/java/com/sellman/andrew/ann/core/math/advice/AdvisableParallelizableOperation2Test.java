package com.sellman.andrew.ann.core.math.advice;

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

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.function.FixedValueFunction;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizableOperation2Test {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizableOperation2<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

	private Matrix a;
	private Function f;

	@Mock
	private ParallelizableOperation2Advisor advisor;

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
		Whitebox.setInternalState(op, ParallelizableOperation2Advisor.class, advisor);

		rowTasks = Arrays.asList(rowTask);
		doReturn(rowTasks).when(byRowTaskPool).borrow(1);

		a = new Matrix(1, 2);
		f = new FixedValueFunction();
	}

	@Test
	public void getSequentialNanos() {
		long elapsed = op.getSequentialOpNanos(a, f);
		assertTrue(elapsed > 0);
	}

	@Test
	public void getParallelOpNanos() {
		long elapsed = op.getParallelOpNanos(a, f);
		assertTrue(elapsed > 0);
	}

	@Test
	public void doAsParallelOp() {
		doReturn(true).when(advisor).doAsParrallelOp(eq(op), eq(1), eq(2), eq(f));

		boolean doInParallel = op.doAsParallelOp(a, f);
		assertTrue(doInParallel);
	}
}
