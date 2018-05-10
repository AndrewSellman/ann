package com.sellman.andrew.vann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class ParallelizableOperation3<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {

	protected ParallelizableOperation3(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
	}

	public Matrix doOperation(final InspectableMatrix m) {
		if (doAsParallelOp(m)) {
			return doParallelOp(m);
		}

		return doSequentialOp(m);
	}

	public Matrix doOperation(final ColumnVector v) {
		return doOperation(v.getMatrix());
	}

	protected final Matrix doParallelOp(final InspectableMatrix m) {
		int sourceRowCount = m.getRowCount();
		int sourceColumnCount = m.getColumnCount();
		return doParallelOp(m, sourceRowCount, sourceColumnCount);
	}

	protected final Matrix doSequentialOp(final InspectableMatrix m) {
		int sourceRowCount = m.getRowCount();
		int sourceColumnCount = m.getColumnCount();
		return doSequentialOp(m, sourceRowCount, sourceColumnCount);
	}

	protected abstract Matrix doSequentialOp(InspectableMatrix m, int targetRowCount, int targetColumnCount);
	
	protected abstract Matrix getTarget(int sourceRowCount, int sourceColumnCount);

	protected abstract boolean doAsParallelOp(final InspectableMatrix m);

	private final Matrix doParallelOp(final InspectableMatrix source, final int sourceRowCount, final int sourceColumnCount) {
		Matrix target = getTarget(sourceRowCount, sourceColumnCount);
		
		if (sourceRowCount <= sourceColumnCount) {
			return doParallelOpByRow(source, sourceRowCount, target);
		}

		return doParallelOpByColumn(source, sourceColumnCount, target);
	}

	private Matrix doParallelOpByColumn(final InspectableMatrix m, final int targetColumnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, m, target, columnIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(getByColumnExceptionContext(m, targetColumnCount, target), e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private Matrix doParallelOpByRow(final InspectableMatrix m, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, m, target, rowIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(getByRowExceptionContext(m, targetRowCount, target), e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private void populateTask(R task, CountDownLatch taskGroup, InspectableMatrix m, Matrix target, int rowIndex) {
		populateTask(task, taskGroup, m, null, null, target, null, rowIndex);
	}

	private void populateTask(C task, CountDownLatch taskGroup, InspectableMatrix m, Matrix target, int columnIndex) {
		populateTask(task, taskGroup, m, null, null, target, null, columnIndex);
	}

	private String getByRowExceptionContext(InspectableMatrix m, int targetRowCount, Matrix target) {
		return toStringByRow(m, null, null, targetRowCount, target, null);
	}

	private String getByColumnExceptionContext(InspectableMatrix m, int targetColumnCount, Matrix target) {
		return toStringByColumn(m, null, null, targetColumnCount, target, null);
	}

	@Override
	public void close() {
		super.close();
	}

}
