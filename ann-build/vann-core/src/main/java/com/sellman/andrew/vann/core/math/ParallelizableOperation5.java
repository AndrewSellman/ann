package com.sellman.andrew.vann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation5<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation5(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public void doOperation(final Matrix source, final Matrix target) {
		if (doAsParallelOp(source)) {
			doParallelOp(source, target);
			return;
		}

		doSequentialOp(source, target);
	}

	public void doOperation(final ColumnVector source, final ColumnVector target) {
		doOperation(source.getMatrix(), target.getMatrix());
	}

	public void doOperation(final RowVector source, final RowVector target) {
		doOperation(source.getMatrix(), target.getMatrix());
	}

	protected final void doParallelOp(final Matrix source, final Matrix target) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		doParallelOp(source, rowCount, columnCount, target);
	}

	protected final void doSequentialOp(final Matrix source, final Matrix target) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		doSequentialOp(source, rowCount, columnCount, target);
	}

	protected abstract void doSequentialOp(final Matrix source, final int rowCount, final int columnCount, final Matrix target);

	protected abstract boolean doAsParallelOp(final Matrix source);

	private void doParallelOp(final Matrix source, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			doParallelOpByRow(source, rowCount, target);
			return;
		}

		doParallelOpByColumn(source, columnCount, target);
	}

	private void doParallelOpByColumn(final Matrix source, final int columnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(columnCount);
			CountDownLatch taskGroup = new CountDownLatch(columnCount);

			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, source, target, columnIndex);
			}

			runTasks(tasks);
		} catch (Exception e) {
			throw new RuntimeException(getByColumnExceptionContext(source, columnCount, target), e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private void doParallelOpByRow(final Matrix source, final int rowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(rowCount);
			CountDownLatch taskGroup = new CountDownLatch(rowCount);

			for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, source, target, rowIndex);
			}

			runTasks(tasks);

		} catch (Exception e) {
			throw new RuntimeException(getByRowExceptionContext(source, rowCount, target), e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private final void populateTask(final R task, final CountDownLatch taskGroup, final Matrix source, final Matrix target, int rowIndex) {
		populateTask(task, taskGroup, source, null, null, target, null, rowIndex);
	}

	private void populateTask(final C task, final CountDownLatch taskGroup, Matrix source, final Matrix target, final int columnIndex) {
		populateTask(task, taskGroup, source, null, null, target, null, columnIndex);
	}

	private String getByRowExceptionContext(Matrix source, int rowCount, Matrix target) {
		return toStringByRow(source, null, null, rowCount, target, null);
	}

	private String getByColumnExceptionContext(Matrix source, int columnCount, Matrix target) {
		return toStringByColumn(source, null, null, columnCount, target, null);
	}

	@Override
	public void close() {
		super.close();
	}

}
