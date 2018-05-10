package com.sellman.andrew.vann.core.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.doThrow;

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

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation2;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation2Test {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ParallelizableOperation2<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

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

	private Matrix m;
	private ColumnVector v;

	@Mock
	private CountDownLatch taskGroup;

	@Mock
	private Function f;

	@Mock
	private Matrix matrixTarget;

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
	public void doVectorWithFunctionOperationAsSequential() throws Exception {
		v = new ColumnVector(1);
		doReturn(matrixTarget).when(op).doSequentialOp(eq(v.getMatrix()), eq(f), eq(1), eq(1), any(Matrix.class));

		ColumnVector result = op.doOperation(v, f);
		assertEquals(matrixTarget, result.getMatrix());
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doMatrixWithFunctionOperationAsSequential() throws Exception {
		m = new Matrix(2, 1);
		doReturn(matrixTarget).when(op).doSequentialOp(eq(m), eq(f), eq(2), eq(1), any(Matrix.class));

		Matrix result = op.doOperation(m, f);
		assertEquals(matrixTarget, result);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doVectorWithFunctionAsParallel() throws Exception {
		v = new ColumnVector(1);
		setOpAsParallel(true);

		ColumnVector result = op.doOperation(v, f);
		assertEquals(1, result.getRowCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(v.getMatrix()), eq(null), eq(f), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doMatrixWithFunctionOperationAsParallelByColumn() throws Exception {
		m = new Matrix(2, 1);
		setOpAsParallel(true);

		Matrix result = op.doOperation(m, f);
		assertEquals(2, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		verify(byColumnTaskPool).borrow(1);
		verify(byColumnTaskPool).recycle(columnTasks);
		verifyZeroInteractions(byRowTaskPool);
		verify(op).populateTask(eq(columnTask), any(CountDownLatch.class), eq(m), eq(null), eq(f), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(columnTasks);
	}

	@Test
	public void doMatrixWithMatrixOperationAsParallelByRow() throws Exception {
		m = new Matrix(1, 2);
		setOpAsParallel(true);

		Matrix result = op.doOperation(m, f);
		assertEquals(1, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(m), eq(null), eq(f), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doOperationAsParallelByColumnWhenAnExceptionIsThrown() throws Exception {
		m = new Matrix(2, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byColumnTaskPool).borrow(1);

		try {
			op.doOperation(m, f);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(m.toString()));
			assertTrue(e.getMessage().contains("f="));
			assertTrue(e.getMessage().contains(f.toString()));
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
		m = new Matrix(1, 2);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byRowTaskPool).borrow(1);

		try {
			op.doOperation(m, f);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(m.toString()));
			assertTrue(e.getMessage().contains("f="));
			assertTrue(e.getMessage().contains(f.toString()));
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
		if (m != null) {
			doReturn(asParallel).when(op).doAsParallelOp(m, f);
		} else if (v != null) {
			doReturn(asParallel).when(op).doAsParallelOp(v.getMatrix(), f);
		}
	}

}
