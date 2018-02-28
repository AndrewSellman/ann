package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class ParallelizableOperation5<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {
	private final ParallelizableOperation5Advisor advisor;

	protected ParallelizableOperation5(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation5Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final void doOperation(final Matrix source, final Matrix target) {
		if (doAsParallelOp(source)) {
			doParallelOp(source, target);
		}

		doSequentialOp(source, target);
	}

	public final void doOperation(final Vector source, final Vector target) {
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

	abstract protected void doSequentialOp(final Matrix source, final int rowCount, final int columnCount, final Matrix target);

	private final boolean doAsParallelOp(final Matrix source) {
		return advisor.doAsParrallelOp(this, source.getRowCount(), source.getColumnCount());
	}

	private void doParallelOp(final Matrix source, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			doParallelOpByRow(source, rowCount, target);
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
			throw new RuntimeException(e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private void doParallelOpByRow(final Matrix source, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, source, target, rowIndex);
			}

			runTasks(tasks);

		} catch (Exception e) {
			throw new RuntimeException(e);
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

}
