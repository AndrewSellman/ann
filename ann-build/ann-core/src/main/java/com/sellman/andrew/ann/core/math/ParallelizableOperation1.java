package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation1<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation1(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public Matrix doOperation(final Matrix a, final Matrix b) {
		if (doAsParallelOp(a, b)) {
			return doParallelOp(a, b);
		}

		return doSequentialOp(a, b);
	}

	public Vector doOperation(final Vector a, final Vector b) {
		return new Vector(doOperation(a.getMatrix(), b.getMatrix()));
	}

	public Vector doOperation(final Matrix a, final Vector b) {
		return new Vector(doOperation(a, b.getMatrix()));
	}

	public Matrix doOperation(final Vector a, final Matrix b) {
		return doOperation(a.getMatrix(), b);
	}

	protected final Matrix doParallelOp(final Matrix a, final Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doParallelOp(a, b, targetRowCount, targetColumnCount, target);
	}

	protected final Matrix doSequentialOp(final Matrix a, final Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doSequentialOp(a, b, targetRowCount, targetColumnCount, target);
	}

	protected abstract Matrix doSequentialOp(Matrix a, Matrix b, int targetRowCount, int targetColumnCount, Matrix target);

	protected abstract boolean doAsParallelOp(final Matrix a, final Matrix b);

	private Matrix doParallelOp(final Matrix a, final Matrix b, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return doParallelOpByRow(a, b, targetRowCount, target);
		}

		return doParallelOpByColumn(a, b, targetColumnCount, target);
	}

	private Matrix doParallelOpByColumn(final Matrix a, final Matrix b, final int targetColumnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, a, b, target, columnIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private Matrix doParallelOpByRow(final Matrix a, final Matrix b, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, a, b, target, rowIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private void populateTask(final R task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, final Matrix target, final int rowIndex) {
		populateTask(task, taskGroup, a, b, null, target, null, rowIndex);
	}

	private void populateTask(final C task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, final Matrix target, final int columnIndex) {
		populateTask(task, taskGroup, a, b, null, target, null, columnIndex);
	}

}
