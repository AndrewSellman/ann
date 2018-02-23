package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class StandardMultiplier extends ParallelizableOperation {
	private final StandardMultiplyByRowTaskPool rowTaskPool;
	private final StandardMultiplyByColumnTaskPool columnTaskPool;

	public StandardMultiplier(TaskService taskService, StandardMultiplyByRowTaskPool rowTaskPool, StandardMultiplyByColumnTaskPool columnTaskPool, ParallelizableOperationAdvisor advisor) {
		super(taskService, advisor);
		this.rowTaskPool = rowTaskPool;
		this.columnTaskPool = columnTaskPool;
	}

	public Matrix multiply(final Matrix left, final Matrix right) {
		if (doAsParallelOp(left, right)) {
			return doParallelOp(left, right);
		}

		return doSequentialOp(left, right);
	}

	public Vector multiply(final Matrix left, final Vector right) {
		return new Vector(multiply(left, right.getMatrix()));
	}

	public Matrix multiply(final Vector left, final Matrix right) {
		return (multiply(left.getMatrix(), right));
	}

	@Override
	protected Matrix doParallelOp(Matrix left, Matrix right) {
		int targetRowCount = left.getRowCount();
		int targetColumnCount = right.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return multiplyParallel(left, right, targetRowCount, targetColumnCount, target);
	}

	@Override
	protected Matrix doSequentialOp(Matrix left, Matrix right) {
		int targetRowCount = left.getRowCount();
		int targetColumnCount = right.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return multiplySequential(left, right, targetRowCount, targetColumnCount, target);
	}

	private Matrix multiplyParallel(final Matrix left, final Matrix right, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return multiplyParallelByRow(left, right, targetRowCount, target);
		}

		return multiplyParallelByColumn(left, right, targetColumnCount, target);
	}

	private Matrix multiplyParallelByRow(Matrix left, Matrix right, int targetRowCount, Matrix target) {
		List<StandardMultiplyByRowTask> tasks = null;
		try {
			tasks = rowTaskPool.borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				StandardMultiplyByRowTask task = tasks.get(rowIndex);
				populateTask(task, taskGroup, left, right, target);
				task.setRowIndex(rowIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(rowTaskPool, tasks);
		}
	}

	private Matrix multiplyParallelByColumn(Matrix left, Matrix right, int targetColumnCount, Matrix target) {
		List<StandardMultiplyByColumnTask> tasks = null;
		try {
			tasks = columnTaskPool.borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				StandardMultiplyByColumnTask task = tasks.get(columnIndex);
				populateTask(task, taskGroup, left, right, target);
				task.setColumnIndex(columnIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(columnTaskPool, tasks);
		}
	}

	private Matrix multiplySequential(final Matrix left, final Matrix right, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		double result;
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {

				result = 0;
				for (int k = 0; k < left.getColumnCount(); k++) {
					result += left.getValue(rowIndex, k) * right.getValue(k, columnIndex);
				}
				target.setValue(rowIndex, columnIndex, result);

			}
		}

		return target;
	}

	@Override
	public void close() throws Exception {
		System.out.println("Closing StandardMultiplier " + toString() + "...");
		rowTaskPool.close();
		columnTaskPool.close();
		System.out.println("Closed StandardMultiplier " + toString());
	}

}
