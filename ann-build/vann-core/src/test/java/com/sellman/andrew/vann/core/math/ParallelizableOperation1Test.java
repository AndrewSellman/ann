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
import com.sellman.andrew.vann.core.math.ParallelizableOperation1;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation1Test {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ParallelizableOperation1<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

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
	private Matrix b;
	private ColumnVector x;
	private ColumnVector y;

	@Mock
	private CountDownLatch taskGroup;

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
	public void doVectorWithMatrixOperationAsSequential() throws Exception {
		x = new ColumnVector(2);
		b = new Matrix(2, 1);
		doReturn(matrixTarget).when(op).doSequentialOp(eq(x.getMatrix()), eq(b), eq(2), eq(1), any(Matrix.class));

		Matrix result = op.doOperation(x, b);
		assertEquals(matrixTarget, result);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doMatrixWithVectorOperationAsSequential() throws Exception {
		a = new Matrix(2, 1);
		y = new ColumnVector(2);
		doReturn(matrixTarget).when(op).doSequentialOp(eq(a), eq(y.getMatrix()), eq(2), eq(1), any(Matrix.class));

		ColumnVector result = op.doOperation(a, y);
		assertEquals(matrixTarget, result.getMatrix());
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doMatrixWithMatrixOperationAsSequential() throws Exception {
		a = new Matrix(2, 1);
		b = new Matrix(2, 1);
		doReturn(matrixTarget).when(op).doSequentialOp(eq(a), eq(b), eq(2), eq(1), any(Matrix.class));

		Matrix result = op.doOperation(a, b);
		assertEquals(matrixTarget, result);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doVectorWithMatrixOperationAsParallel() throws Exception {
		x = new ColumnVector(1);
		b = new Matrix(1, 1);
		setOpAsParallel(true);

		Matrix result = op.doOperation(x, b);
		assertEquals(1, result.getRowCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(x.getMatrix()), eq(b), eq(null), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doMatrixWithVectorOperationAsParallel() throws Exception {
		a = new Matrix(1, 1);
		y = new ColumnVector(1);
		setOpAsParallel(true);

		ColumnVector result = op.doOperation(a, y);
		assertEquals(1, result.getRowCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(a), eq(y.getMatrix()), eq(null), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doVectorWithVectorOperationAsParallel() throws Exception {
		x = new ColumnVector(1);
		y = new ColumnVector(1);
		setOpAsParallel(true);

		ColumnVector result = op.doOperation(x, y);
		assertEquals(1, result.getRowCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(x.getMatrix()), eq(y.getMatrix()), eq(null), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doMatrixWithMatrixOperationAsParallelByColumn() throws Exception {
		a = new Matrix(2, 1);
		b = new Matrix(2, 1);
		setOpAsParallel(true);

		Matrix result = op.doOperation(a, b);
		assertEquals(2, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		verify(byColumnTaskPool).borrow(1);
		verify(byColumnTaskPool).recycle(columnTasks);
		verifyZeroInteractions(byRowTaskPool);
		verify(op).populateTask(eq(columnTask), any(CountDownLatch.class), eq(a), eq(b), eq(null), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(columnTasks);
	}

	@Test
	public void doMatrixWithMatrixOperationAsParallelByRow() throws Exception {
		a = new Matrix(1, 2);
		b = new Matrix(1, 2);
		setOpAsParallel(true);

		Matrix result = op.doOperation(a, b);
		assertEquals(1, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(a), eq(b), eq(null), any(Matrix.class), eq(null), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doOperationAsParallelByColumnWhenAnExceptionIsThrown() throws Exception {
		a = new Matrix(2, 1);
		b = new Matrix(2, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byColumnTaskPool).borrow(1);

		try {
			op.doOperation(a, b);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(a.toString()));
			assertTrue(e.getMessage().contains("b="));
			assertTrue(e.getMessage().contains(b.toString()));
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
		b = new Matrix(1, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byRowTaskPool).borrow(1);

		try {
			op.doOperation(a, b);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(a.toString()));
			assertTrue(e.getMessage().contains("b="));
			assertTrue(e.getMessage().contains(b.toString()));
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
		if (a != null && b != null) {
			doReturn(asParallel).when(op).doAsParallelOp(a, b);
		} else if (x != null && y != null) {
			doReturn(asParallel).when(op).doAsParallelOp(x.getMatrix(), y.getMatrix());
		} else if (a != null && y != null) {
			doReturn(asParallel).when(op).doAsParallelOp(a, y.getMatrix());
		} else if (b != null && x != null) {
			doReturn(asParallel).when(op).doAsParallelOp(x.getMatrix(), b);
		}
	}

}
