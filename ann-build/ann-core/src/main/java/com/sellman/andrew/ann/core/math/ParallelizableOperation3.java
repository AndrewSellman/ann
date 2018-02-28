package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class ParallelizableOperation3<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation<R, C> {
	private final ParallelizableOperation3Advisor advisor;

	protected ParallelizableOperation3(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation3Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final Matrix doOperation(final Matrix m) {
		if (doAsParallelOp(m)) {
			return doParallelOp(m);
		}

		return doSequentialOp(m);
	}

	public final Matrix doOperation(final Vector v) {
		return doOperation(v.getMatrix());
	}

	protected final Matrix doParallelOp(final Matrix m) {
		int sourceRowCount = m.getRowCount();
		int sourceColumnCount = m.getColumnCount();
		return doParallelOp(m, sourceRowCount, sourceColumnCount);
	}

	protected final Matrix doSequentialOp(final Matrix m) {
		int sourceRowCount = m.getRowCount();
		int sourceColumnCount = m.getColumnCount();
		return doSequentialOp(m, sourceRowCount, sourceColumnCount);
	}

	abstract protected Matrix doSequentialOp(Matrix m, int targetRowCount, int targetColumnCount);
	
	abstract Matrix getTarget(int sourceRowCount, int sourceColumnCount);

	private final boolean doAsParallelOp(final Matrix m) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount());
	}

	private final Matrix doParallelOp(final Matrix source, final int sourceRowCount, final int sourceColumnCount) {
		Matrix target = getTarget(sourceRowCount, sourceColumnCount);
		
		if (sourceRowCount <= sourceColumnCount) {
			return doParallelOpByRow(source, sourceRowCount, target);
		}

		return doParallelOpByColumn(source, sourceColumnCount, target);
	}

	private Matrix doParallelOpByColumn(final Matrix m, final int targetColumnCount, final Matrix target) {
		List<C> tasks = null;
		try {
			tasks = getOperationByColumnTaskPool().borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				C task = tasks.get(columnIndex);
				populateTask(task, taskGroup, m, target, columnIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByColumnTaskPool().recycle(tasks);
		}
	}

	private Matrix doParallelOpByRow(final Matrix m, final int targetRowCount, final Matrix target) {
		List<R> tasks = null;
		try {
			tasks = getOperationByRowTaskPool().borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				R task = tasks.get(rowIndex);
				populateTask(task, taskGroup, m, target, rowIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getOperationByRowTaskPool().recycle(tasks);
		}
	}

	private void populateTask(R task, CountDownLatch taskGroup, Matrix m, Matrix target, int rowIndex) {
		populateTask(task, taskGroup, m, null, null, target, null, rowIndex);
	}

	private void populateTask(C task, CountDownLatch taskGroup, Matrix m, Matrix target, int columnIndex) {
		populateTask(task, taskGroup, m, null, null, target, null, columnIndex);
	}

}
