package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class HadamardMultiplier extends ParallelizableOperation implements AutoCloseable {
	private final HadamardMultiplyByRowTaskPool rowTaskPool;
	private final HadamardMultiplyByColumnTaskPool columnTaskPool;

	public HadamardMultiplier(TaskService taskService, HadamardMultiplyByRowTaskPool rowTaskPool, HadamardMultiplyByColumnTaskPool columnTaskPool, ParallelizableOperationAdvisor advisor) {
		super(taskService, advisor);
		this.rowTaskPool = rowTaskPool;
		this.columnTaskPool = columnTaskPool;
	}

	public Matrix multiply(final Matrix a, final Matrix b) {
		if (doAsParallelOp(a, b)) {
			return doParallelOp(a, b);
		}

		return doSequentialOp(a, b);
	}

	public Vector multiply(final Vector a, final Vector b) {
		return new Vector(multiply(a.getMatrix(), b.getMatrix()));
	}

	@Override
	protected Matrix doParallelOp(Matrix a, Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = a.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return multiplyParallel(a, b, targetRowCount, targetColumnCount, target);
	}

	@Override
	protected Matrix doSequentialOp(Matrix a, Matrix b) {
		int targetRowCount = a.getRowCount();
		int targetColumnCount = a.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return multiplySequential(a, b, targetRowCount, targetColumnCount, target);
	}

	private Matrix multiplyParallel(final Matrix a, final Matrix b, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return multiplyParallelByRow(a, b, targetRowCount, target);
		}

		return multiplyParallelByColumn(a, b, targetColumnCount, target);
	}

	private Matrix multiplyParallelByRow(final Matrix a, final Matrix b, final int targetRowCount, final Matrix target) {
		List<HadamardMultiplyByRowTask> tasks = null;
		try {
			tasks = rowTaskPool.borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				HadamardMultiplyByRowTask task = tasks.get(rowIndex);
				populateTask(task, taskGroup, a, b, target);
				task.setRowIndex(rowIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(rowTaskPool, tasks);
		}
	}

	private Matrix multiplyParallelByColumn(final Matrix a, final Matrix b, final int targetColumnCount, final Matrix target) {
		List<HadamardMultiplyByColumnTask> tasks = null;
		try {
			tasks = columnTaskPool.borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				HadamardMultiplyByColumnTask task = tasks.get(columnIndex);
				populateTask(task, taskGroup, a, b, target);
				task.setColumnIndex(columnIndex);
			}

			return runTasksAndReturnTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(columnTaskPool, tasks);
		}
	}

	private Matrix multiplySequential(final Matrix a, final Matrix b, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

	@Override
	public void close() throws Exception {
		System.out.println("Closing HadamardMultiplier " + toString() + "...");
		rowTaskPool.close();
		columnTaskPool.close();
		System.out.println("Closed HadamardMultiplier " + toString());
	}

}
