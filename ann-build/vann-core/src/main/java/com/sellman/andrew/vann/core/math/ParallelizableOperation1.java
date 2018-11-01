package com.sellman.andrew.vann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation1<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation1(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public Matrix doOperation(final InspectableMatrix a, final InspectableMatrix b) {
		if (doAsParallelOp(a, b)) {
			return doParallelOp(a, b);
		}

		return doSequentialOp(a, b);
	}

	public RowVector doOperation(final RowVector a, final RowVector b) {
		return new RowVector(doOperation(a.getMatrix(), b.getMatrix()));
	}

	public ColumnVector doOperation(final ColumnVector a, final ColumnVector b) {
		return new ColumnVector(doOperation(a.getMatrix(), b.getMatrix()));
	}

	public ColumnVector doOperation(final Matrix a, final ColumnVector b) {
		return new ColumnVector(doOperation(a, b.getMatrix()));
	}

	public Matrix doOperation(final ColumnVector a, final Matrix b) {
		return doOperation(a.getMatrix(), b);
	}

	protected Matrix doParallelOp(final InspectableMatrix a, final InspectableMatrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doParallelOp(a, b, targetRowCount, targetColumnCount, target);
	}

	protected Matrix doSequentialOp(final InspectableMatrix a, final InspectableMatrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doSequentialOp(a, b, targetRowCount, targetColumnCount, target);
	}

	protected abstract Matrix doSequentialOp(InspectableMatrix a, InspectableMatrix b, int targetRowCount, int targetColumnCount, Matrix target);

	protected abstract boolean doAsParallelOp(final InspectableMatrix a, final InspectableMatrix b);

	private Matrix doParallelOp(final InspectableMatrix a, final InspectableMatrix b, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return doParallelOpByRow(a, b, targetRowCount, target);
		}

		return doParallelOpByColumn(a, b, targetColumnCount, target);
	}

	private Matrix doParallelOpByColumn(final InspectableMatrix a, final InspectableMatrix b, final int targetColumnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, a, b, target, columnIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(getByColumnExceptionContext(a, b, targetColumnCount, target), e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private Matrix doParallelOpByRow(final InspectableMatrix a, final InspectableMatrix b, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, a, b, target, rowIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(getByRowExceptionContext(a, b, targetRowCount, target), e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private void populateTask(final R task, final CountDownLatch taskGroup, final InspectableMatrix a, final InspectableMatrix b, final Matrix target, final int rowIndex) {
		populateTask(task, taskGroup, a, b, null, target, null, rowIndex);
	}

	private void populateTask(final C task, final CountDownLatch taskGroup, final InspectableMatrix a, final InspectableMatrix b, final Matrix target, final int columnIndex) {
		populateTask(task, taskGroup, a, b, null, target, null, columnIndex);
	}

	private String getByRowExceptionContext(InspectableMatrix a, InspectableMatrix b, int targetRowCount, Matrix target) {
		return toStringByRow(a, b, null, targetRowCount, target, null);
	}

	private String getByColumnExceptionContext(InspectableMatrix a, InspectableMatrix b, int targetColumnCount, Matrix target) {
		return toStringByColumn(a, b, null, targetColumnCount, target, null);
	}

	@Override
	public void close() {
		super.close();
	}

}
