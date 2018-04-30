package com.sellman.andrew.ann.core.math;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.function.FixedValueFunction;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationTask;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperationTest {
	private static final int INDEX = 1;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ParallelizableOperation<AbstractOperationByRowTask, AbstractOperationByColumnTask> op;

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

	@Mock
	private Function f;

	@Mock
	private Matrix a;

	@Mock
	private Matrix b;

	@Mock
	private CountDownLatch taskGroup;

	@Mock
	private Matrix matrixTarget;

	@Mock
	private Vector vectorTarget;

	@Before
	public void prepareTest() {
		Whitebox.setInternalState(op, TaskService.class, taskService);
		Whitebox.setInternalState(op, AbstractOperationByRowTaskPool.class, byRowTaskPool);
		Whitebox.setInternalState(op, AbstractOperationByColumnTaskPool.class, byColumnTaskPool);
	}

	@Test
	public void toStringByColumnWhenVectorTargetIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = null;

		String s = op.toStringByColumn(a, b, f, 42, matrixTarget, vectorTarget);
		assertByColumnString(s, 42);
	}

	@Test
	public void toStringByRowWhenVectorTargetIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = null;

		String s = op.toStringByRow(a, b, f, 42, matrixTarget, vectorTarget);
		assertByRowString(s, 42);
	}

	@Test
	public void toStringByColumnWhenMatrixTargetIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = null;

		String s = op.toStringByColumn(a, b, f, 42, matrixTarget, vectorTarget);
		assertByColumnString(s, 42);
	}

	@Test
	public void toStringByRowWhenMatrixTargetIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = null;
		vectorTarget = new Vector(5);

		String s = op.toStringByRow(a, b, f, 42, matrixTarget, vectorTarget);
		assertByRowString(s, 42);
	}

	@Test
	public void toStringByColumnWhenFunctionIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = null;
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByColumn(a, b, f, 42, matrixTarget, vectorTarget);
		assertByColumnString(s, 42);
	}

	@Test
	public void toStringByRowWhenFunctionIsNull() {
		a = new Matrix(1, 2);
		b = new Matrix(2, 1);
		f = null;
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByRow(a, b, f, 42, matrixTarget, vectorTarget);
		assertByRowString(s, 42);
	}

	@Test
	public void toStringByColumnWhenMatrixBIsNull() {
		a = new Matrix(2, 1);
		b = null;
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByColumn(a, b, f, 42, matrixTarget, vectorTarget);
		assertByColumnString(s, 42);
	}

	@Test
	public void toStringByRowWhenMatrixBIsNull() {
		a = new Matrix(2, 1);
		b = null;
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByRow(a, b, f, 42, matrixTarget, vectorTarget);
		assertByRowString(s, 42);
	}

	@Test
	public void toStringByColumnWhenMatrixAIsNull() {
		a = null;
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByColumn(a, b, f, 42, matrixTarget, vectorTarget);
		assertByColumnString(s, 42);
	}

	@Test
	public void toStringByRowWhenMatrixAIsNull() {
		a = null;
		b = new Matrix(2, 1);
		f = new FixedValueFunction();
		matrixTarget = new Matrix(2, 3);
		vectorTarget = new Vector(5);

		String s = op.toStringByRow(a, b, f, 42, matrixTarget, vectorTarget);
		assertByRowString(s, 42);
	}

	@Test
	public void close() {
		op.close();
		verify(byRowTaskPool).close();
		verify(byColumnTaskPool).close();
	}

	@Test
	public void getOperationByColumnTaskPool() {
		assertEquals(byColumnTaskPool, op.getOperationByColumnTaskPool());
	}

	@Test
	public void getOperationByRowTaskPool() {
		assertEquals(byRowTaskPool, op.getOperationByRowTaskPool());
	}

	@Test
	public void runTasksAndGetVectorTarget() {
		List<AbstractOperationTask> tasks = Arrays.asList(rowTask, columnTask);

		Vector target = op.runTasksAndReturnTarget(tasks, vectorTarget);
		verify(taskService).runTasks(tasks);
		assertEquals(vectorTarget, target);
	}

	@Test
	public void runTasksAndGetMatrixTarget() {
		List<AbstractOperationTask> tasks = Arrays.asList(rowTask, columnTask);

		Matrix target = op.runTasksAndReturnTarget(tasks, matrixTarget);
		verify(taskService).runTasks(tasks);
		assertEquals(matrixTarget, target);
	}

	@Test
	public void runTasks() {
		List<AbstractOperationTask> tasks = Arrays.asList(rowTask, columnTask);

		op.runTasks(tasks);
		verify(taskService).runTasks(tasks);
	}

	@Test
	public void populateColumnTask() {
		op.populateTask(columnTask, taskGroup, a, b, f, matrixTarget, vectorTarget, INDEX);
		assertEquals(INDEX, columnTask.getColumnIndex());
		assertTaskCommonState(columnTask);
	}

	@Test
	public void populateRowTask() {
		op.populateTask(rowTask, taskGroup, a, b, f, matrixTarget, vectorTarget, INDEX);
		assertEquals(INDEX, rowTask.getRowIndex());
		assertTaskCommonState(rowTask);
	}

	private void assertTaskCommonState(AbstractOperationTask task) {
		assertEquals(taskGroup, task.getTaskGroup());
		assertEquals(a, task.getMatrixA());
		assertEquals(b, task.getMatrixB());
		assertEquals(f, task.getFunction());
		assertEquals(matrixTarget, task.getMatrixTarget());
		assertEquals(vectorTarget, task.getVectorTarget());
	}

	private void assertString(String s) {
		if (a == null) {
			assertFalse(s.contains("a="));
		} else {
			assertTrue(s.contains("a="));
			assertTrue(s.contains(a.toString()));
		}

		if (b == null) {
			assertFalse(s.contains("b="));
		} else {
			assertTrue(s.contains("b="));
			assertTrue(s.contains(b.toString()));
		}

		if (f == null) {
			assertFalse(s.contains("f="));
		} else {
			assertTrue(s.contains("f="));
			assertTrue(s.contains(f.toString()));
		}

		if (matrixTarget == null && vectorTarget == null) {
			assertFalse(s.contains("target="));
		}
		
		if (matrixTarget != null) {
			assertTrue(s.contains("target="));
			assertTrue(s.contains(matrixTarget.toString()));
		}

		if (vectorTarget != null) {
			assertTrue(s.contains("target="));
			assertTrue(s.contains(vectorTarget.toString()));
		}

	}

	private void assertByColumnString(String s, int targetColumnCount) {
		assertTrue(s.contains("targetColumnCount=" + targetColumnCount));
		assertString(s);
	}

	private void assertByRowString(String s, int targetRowCount) {
		assertTrue(s.contains("targetRowCount=" + targetRowCount));
		assertString(s);
	}

}
