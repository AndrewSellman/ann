package com.sellman.andrew.vann.core.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation4;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperation4Test {
	private static final double RESULT = 42.0;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ParallelizableOperation4<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

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
	private Vector v;

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
	public void doVectorOperationAsSequential() throws Exception {
		v = new Vector(1);
		doReturn(RESULT).when(op).doSequentialOp(eq(v.getMatrix()), eq(1), eq(1));

		double result = op.doOperation(v);
		assertEquals(RESULT, result, 0);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doMatrixOperationAsSequential() throws Exception {
		m = new Matrix(2, 1);
		doReturn(RESULT).when(op).doSequentialOp(eq(m), eq(2), eq(1));

		double result = op.doOperation(m);
		assertEquals(RESULT, result, 0);
		verifyZeroInteractions(byColumnTaskPool);
		verifyZeroInteractions(byRowTaskPool);
		verifyZeroInteractions(taskService);
	}

	@Test
	public void doVectorOperationAsParallel() throws Exception {
		v = new Vector(2);
		setOpAsParallel(true);
		doReturn(RESULT).when(op).doSequentialOp(any(Matrix.class), eq(1), eq(1));

		double result = op.doOperation(v);
		assertEquals(RESULT, result, 0);
		verify(byColumnTaskPool).borrow(1);
		verify(byColumnTaskPool).recycle(columnTasks);
		verifyZeroInteractions(byRowTaskPool);
		verify(op).populateTask(eq(columnTask), any(CountDownLatch.class), eq(v.getMatrix()), eq(null), eq(null), eq(null), any(Vector.class), eq(0));
		verify(taskService).runTasks(columnTasks);
	}

	@Test
	public void doMatrixOperationAsParallelByColumn() throws Exception {
		m = new Matrix(2, 1);
		setOpAsParallel(true);
		doReturn(RESULT).when(op).doSequentialOp(any(Matrix.class), eq(1), eq(1));

		double result = op.doOperation(m);
		assertEquals(RESULT, result, 0);
		verify(byColumnTaskPool).borrow(1);
		verify(byColumnTaskPool).recycle(columnTasks);
		verifyZeroInteractions(byRowTaskPool);
		verify(op).populateTask(eq(columnTask), any(CountDownLatch.class), eq(m), eq(null), eq(null), eq(null), any(Vector.class), eq(0));
		verify(taskService).runTasks(columnTasks);
	}

	@Test
	public void doMatrixOperationAsParallelByRow() throws Exception {
		m = new Matrix(1, 2);
		setOpAsParallel(true);
		doReturn(RESULT).when(op).doSequentialOp(any(Matrix.class), eq(1), eq(1));

		double result = op.doOperation(m);
		assertEquals(RESULT, result, 0);
		verify(byRowTaskPool).borrow(1);
		verify(byRowTaskPool).recycle(rowTasks);
		verifyZeroInteractions(byColumnTaskPool);
		verify(op).populateTask(eq(rowTask), any(CountDownLatch.class), eq(m), eq(null), eq(null), eq(null), any(Vector.class), eq(0));
		verify(taskService).runTasks(rowTasks);
	}

	@Test
	public void doOperationAsParallelByColumnWhenAnExceptionIsThrown() throws Exception {
		m = new Matrix(2, 1);
		setOpAsParallel(true);
		doThrow(new Exception("uh oh!")).when(byColumnTaskPool).borrow(1);

		try {
			op.doOperation(m);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(m.toString()));
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
			op.doOperation(m);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("a="));
			assertTrue(e.getMessage().contains(m.toString()));
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
			doReturn(asParallel).when(op).doAsParallelOp(m);
		} else if (v != null) {
			doReturn(asParallel).when(op).doAsParallelOp(v.getMatrix());
		}
	}

}
