package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation5Test {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ParallelizableOperation5<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

	@Mock
	private TaskService taskService;

	@Mock
	private AbstractOperationByRowTaskPool<AbstractOperationByRowTask> byRowTaskPool;

	@Mock
	private AbstractOperationByColumnTaskPool<AbstractOperationByColumnTask> byColumnTaskPool;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AbstractOperationByRowTask rowTask;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AbstractOperationByColumnTask columnTask;

	private Matrix a;
	private Matrix matrixTarget;
	private Vector x;
	private Vector vectorTarget;

	@Mock
	private CountDownLatch taskGroup;

	private List<AbstractOperationByRowTask> rowTasks;
	private List<AbstractOperationByColumnTask> columnTasks;

	@Before
	public void prepareTest() throws Exception {
		setOpAsParallel(false);

		Whitebox.setInternalState(op, TaskService.class, taskService);
		Whitebox.setInternalState(op, AbstractOperationByRowTaskPool.class, byRowTaskPool);
		Whitebox.setInternalState(op, AbstractOperationByColumnTaskPool.class, byColumnTaskPool);

		rowTasks = Arrays.asList(rowTask);
		doReturn(rowTasks).when(byRowTaskPool).borrow(1);

		columnTasks = Arrays.asList(columnTask);
		doReturn(columnTasks).when(byColumnTaskPool).borrow(1);
	}

	@Test
	public void doVectorWithVectorOperationAsSequential() throws Exception {
		x = new Vector(2);
		vectorTarget = new Vector(2);
		doNothing().when(op).doSequentialOp(eq(x.getMatrix()), eq(2), eq(1), eq(vectorTarget.getMatrix()));

		op.doOperation(x, vectorTarget);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doMatrixWithMatrixOperationAsSequential() throws Exception {
		a = new Matrix(2, 1);
		matrixTarget = new Matrix(2, 1);
		doNothing().when(op).doSequentialOp(eq(a), eq(2), eq(2), eq(matrixTarget));

		op.doOperation(a, matrixTarget);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doVectorWithVectorOperationAsParallel() throws Exception {
		x = new Vector(1);
		vectorTarget = new Vector(1);
		setOpAsParallel(true);

		op.doOperation(x, vectorTarget);
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(x.getMatrix()), eq(null), eq(null), eq(vectorTarget.getMatrix()), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doMatrixWithMatrixOperationAsParallelByColumn() throws Exception {
		a = new Matrix(2, 1);
		matrixTarget = new Matrix(2, 1);
		setOpAsParallel(true);

		op.doOperation(a, matrixTarget);
		verify(byColumnTaskPool).borrow(1);
		verify(byColumnTaskPool).recycle(columnTasks);
		verifyZeroInteractions(byRowTaskPool);
		verify(op).populateTask(eq(columnTask), any(CountDownLatch.class), eq(a), eq(null), eq(null), eq(matrixTarget), eq(null), eq(0));
		verify(taskService).runTasks(columnTasks);
	}

	@Test
	public void doMatrixWithMatrixOperationAsParallelByRow() throws Exception {
		a = new Matrix(1, 2);
		matrixTarget = new Matrix(1, 2);
		setOpAsParallel(true);

		op.doOperation(a, matrixTarget);
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(a), eq(null), eq(null), eq(matrixTarget), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doOperationAsParallelByColumnWhenAnExceptionIsThrown() throws Exception {
		a = new Matrix(2, 1);
		matrixTarget = new Matrix(2, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byColumnTaskPool).borrow(1);

		try {
			op.doOperation(a, matrixTarget);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(a.toString()));
			assertTrue(e.getMessage().contains("target="));
			assertTrue(e.getMessage().contains("targetColumnCount="));
			verify(byColumnTaskPool).borrow(1);
			verify(byColumnTaskPool).recycle(null);
			verifyZeroInteractions(byRowTaskPool);
			verifyZeroInteractions(taskService);
			return;
		}

		fail("Should have thrown a RuntimeException!");
	}

	@Test
	public void doOperationAsParallelByRowWhenAnExceptionIsThrown() throws Exception {
		a = new Matrix(1, 1);
		matrixTarget = new Matrix(1, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byRowTaskPool).borrow(1);

		try {
			op.doOperation(a, matrixTarget);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(a.toString()));
			assertTrue(e.getMessage().contains("target="));
			assertTrue(e.getMessage().contains("targetRowCount="));
			verify(byRowTaskPool).borrow(1);
			verify(byRowTaskPool).recycle(null);
			verifyZeroInteractions(byColumnTaskPool);
			verifyZeroInteractions(taskService);
			return;
		}

		fail("Should have thrown a RuntimeException!");
	}

	@Test
	public void close() {
		op.close();
		verify(byRowTaskPool).close();
		verify(byColumnTaskPool).close();
	}
	
	private void setOpAsParallel(boolean asParallel) {
		if (a != null) {
			doReturn(asParallel).when(op).doAsParallelOp(a);
		} else if (x != null) {
			doReturn(asParallel).when(op).doAsParallelOp(x.getMatrix());
		}
	}

}
