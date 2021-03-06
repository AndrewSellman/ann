package com.sellman.andrew.vann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation4<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation4(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public double doOperation(final InspectableMatrix m) {
		if (doAsParallelOp(m)) {
			return doParallelOp(m);
		}

		return doSequentialOp(m);
	}

	public double doOperation(final ColumnVector v) {
		return doOperation(v.getMatrix());
	}

	protected final double doParallelOp(final InspectableMatrix m) {
		int rowCount = m.getRowCount();
		int columnCount = m.getColumnCount();
		return doParallelOp(m, rowCount, columnCount);
	}

	protected final double doSequentialOp(final InspectableMatrix m) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		return doSequentialOp(m, targetRowCount, targetColumnCount);
	}

	protected abstract double doSequentialOp(InspectableMatrix m, int targetRowCount, int targetColumnCount);

	protected abstract boolean doAsParallelOp(final InspectableMatrix m);

	private double doParallelOp(final InspectableMatrix m, final int rowCount, final int columnCount) {
		if (rowCount <= columnCount) {
			ColumnVector target = new ColumnVector(rowCount);
			return doParallelOpByRow(m, rowCount, target);
		}

		ColumnVector target = new ColumnVector(columnCount);
		return doParallelOpByColumn(m, columnCount, target);
	}

	private double doParallelOpByColumn(final InspectableMatrix m, final int targetColumnCount, final ColumnVector target) {
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
			throw new RuntimeException(getByColumnExceptionContext(m, targetColumnCount, target), e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private double doParallelOpByRow(final InspectableMatrix m, final int targetRowCount, final ColumnVector target) {
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
			throw new RuntimeException(getByRowExceptionContext(m, targetRowCount, target), e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private final void populateTask(final R task, final CountDownLatch taskGroup, final InspectableMatrix m, ColumnVector target, int rowIndex) {
		populateTask(task, taskGroup, m, null, null, null, target, rowIndex);
	}

	private void populateTask(C task, CountDownLatch taskGroup, InspectableMatrix m, ColumnVector target, int columnIndex) {
		populateTask(task, taskGroup, m, null, null, null, target, columnIndex);
	}


	private String getByRowExceptionContext(InspectableMatrix m, int targetRowCount, ColumnVector target) {
		return toStringByRow(m, null, null, targetRowCount, null, target);
	}

	private String getByColumnExceptionContext(InspectableMatrix m, int targetColumnCount, ColumnVector target) {
		return toStringByColumn(m, null, null, targetColumnCount, null, target);
	}

	@Override
	public void close() {
		super.close();
	}

}
