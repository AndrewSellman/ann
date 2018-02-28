package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class ParallelizableOperation1Old extends ParallelizableOperation {
	private final ParallelizableOperation1OldAdvisor advisor;

	protected ParallelizableOperation1Old(final TaskService taskService, final ParallelizableOperation1OldAdvisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	public final Matrix doOperation(final Matrix a, final Matrix b) {
		if (doAsParallelOp(a, b)) {
			return doParallelOp(a, b);
		}

		return doSequentialOp(a, b);
	}

	public final Vector doOperation(final Vector a, final Vector b) {
		return new Vector(doOperation(a.getMatrix(), b.getMatrix()));
	}
	
	public Vector doOperation(Matrix a, Vector b) {
		return new Vector(doOperation(a, b.getMatrix()));
	}
	
	public Matrix doOperation(Vector a, Matrix b) {
		return doOperation(a.getMatrix(), b);
	}

	protected final Matrix doParallelOp(Matrix a, Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doParallelOp(a, b, targetRowCount, targetColumnCount, target);
	}

	private final Matrix doParallelOp(final Matrix a, final Matrix b, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return doParallelOpByRow(a, b, targetRowCount, target);
		}

		return doParallelOpByColumn(a, b, targetColumnCount, target);
	}

	abstract protected Matrix doParallelOpByColumn(Matrix a, Matrix b, int targetColumnCount, Matrix target);

	abstract protected Matrix doParallelOpByRow(Matrix a, Matrix b, int targetRowCount, Matrix target);

	protected final Matrix doSequentialOp(Matrix a, Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = b.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return doSequentialOp(a, b, targetRowCount, targetColumnCount, target);
	}

	protected final void populateTask(final AbstractOperationTask task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Matrix target) {
		task.setTaskGroup(taskGroup);
		task.setMatrixA(a);
		task.setMatrixB(b);
		task.setMatrixTarget(target);
	}

	protected void populateTask(final AbstractOperationByRowTask task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Matrix target, int rowIndex) {
		populateTask(task, taskGroup, a, b, target);
		task.setRowIndex(rowIndex);
	}

	protected final void populateTask(final AbstractOperationByColumnTask task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Matrix target, int columnIndex) {
		populateTask(task, taskGroup, a, b, target);
		task.setColumnIndex(columnIndex);
	}

	protected final boolean doAsParallelOp(final Matrix a, final Matrix b) {
		return advisor.doAsParrallelOp(this, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

	abstract protected Matrix doSequentialOp(Matrix a, Matrix b, int targetRowCount, int targetColumnCount, Matrix target);
	
}
