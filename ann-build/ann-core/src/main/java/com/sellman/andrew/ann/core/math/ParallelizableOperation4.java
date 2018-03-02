package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation4<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation4(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public final double doOperation(final Matrix m) {
		if (doAsParallelOp(m)) {
			return doParallelOp(m);
		}

		return doSequentialOp(m);
	}

	public final double doOperation(final Vector v) {
		return doOperation(v.getMatrix());
	}

	protected final double doParallelOp(final Matrix m) {
		int rowCount = m.getRowCount();
		int columnCount = m.getColumnCount();
		return doParallelOp(m, rowCount, columnCount);
	}

	protected final double doSequentialOp(final Matrix m) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doSequentialOp(m, targetRowCount, targetColumnCount, target);
	}

	protected abstract double doSequentialOp(Matrix m, int targetRowCount, int targetColumnCount, Matrix target);

	protected abstract boolean doAsParallelOp(final Matrix m);

	private double doParallelOp(final Matrix m, final int rowCount, final int columnCount) {
		if (rowCount <= columnCount) {
			Vector target = new Vector(rowCount);
			return doParallelOpByRow(m, rowCount, target);
		}

		Vector target = new Vector(columnCount);
		return doParallelOpByColumn(m, columnCount, target);
	}

	private double doParallelOpByColumn(final Matrix m, final int targetColumnCount, final Vector target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, m, target, columnIndex);
			}

			runTasks(tasks);
			return doSequentialOp(target.getMatrix());

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private double doParallelOpByRow(final Matrix m, final int targetRowCount, final Vector target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, m, target, rowIndex);
			}

			runTasks(tasks);
			return doSequentialOp(target.getMatrix());

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private final void populateTask(final R task, final CountDownLatch taskGroup, final Matrix m, Vector target, int rowIndex) {
		populateTask(task, taskGroup, m, null, null, null, target, rowIndex);
	}

	private void populateTask(C task, CountDownLatch taskGroup, Matrix m, Vector target, int columnIndex) {
		populateTask(task, taskGroup, m, null, null, null, target, columnIndex);
	}

}
