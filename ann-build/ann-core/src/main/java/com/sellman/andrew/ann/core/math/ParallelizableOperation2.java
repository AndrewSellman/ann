package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation2<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation2(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public final Matrix doOperation(final Matrix m, final Function f) {
		if (doAsParallelOp(m, f)) {
			return doParallelOp(m, f);
		}

		return doSequentialOp(m, f);
	}

	public final Vector doOperation(final Vector v, final Function f) {
		return new Vector(doOperation(v.getMatrix(), f));
	}

	protected Matrix doSequentialOp(Matrix m, Function f) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doSequentialOp(m, f, targetRowCount, targetColumnCount, target);
	}

	protected abstract Matrix doSequentialOp(Matrix m, Function f, int targetRowCount, int targetColumnCount, Matrix target);

	protected Matrix doParallelOp(Matrix m, Function f) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doParallelOp(m, f, targetRowCount, targetColumnCount, target);
	}

	private Matrix doParallelOp(final Matrix m, final Function f, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return doParallelOpByRow(m, f, targetRowCount, target);
		}

		return doParallelOpByColumn(m, f, targetColumnCount, target);
	}

	private Matrix doParallelOpByColumn(final Matrix m, final Function f, final int targetColumnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, m, f, target, columnIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private Matrix doParallelOpByRow(final Matrix m, final Function f, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, m, f, target, rowIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	protected abstract boolean doAsParallelOp(final Matrix m, final Function f);

	private void populateTask(final C task, final CountDownLatch taskGroup, final Matrix m, final Function f, final Matrix target, final int columnIndex) {
		populateTask(task, taskGroup, m, null, f, target, null, columnIndex);
	}

	private void populateTask(final R task, final CountDownLatch taskGroup, final Matrix m, final Function f, final Matrix target, final int rowIndex) {
		populateTask(task, taskGroup, m, null, f, target, null, rowIndex);
	}

}
