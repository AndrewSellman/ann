package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class Scaler extends ParallelizableOperation2 {
	private final ScalerByRowTaskPool rowTaskPool;
	private final ScalerByColumnTaskPool columnTaskPool;

	public Scaler(final TaskService taskService, final ParallelizableOperation2Advisor advisor, final ScalerByRowTaskPool rowTaskPool, final ScalerByColumnTaskPool columnTaskPool) {
		super(taskService, advisor);
		this.rowTaskPool = rowTaskPool;
		this.columnTaskPool = columnTaskPool;
	}

	public Matrix scale(final Matrix m, final Function f) {
		if (doAsParallelOp(m, f)) {
			return doParallelOp(m, f);
		}

		return doSequentialOp(m, f);
	}

	public Vector scale(final Vector v, final Function f) {
		return new Vector(scale(v.getMatrix(), f));
	}

	@Override
	protected Matrix doParallelOp(Matrix m, Function f) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return scaleParallel(m, f, targetRowCount, targetColumnCount, target);
	}

	@Override
	protected Matrix doSequentialOp(Matrix m, Function f) {
		int targetRowCount = m.getRowCount();
		int targetColumnCount = m.getColumnCount();
		Matrix target = new Matrix(targetRowCount, targetColumnCount);
		return scaleSequential(m, f, targetRowCount, targetColumnCount, target);
	}

	private Matrix scaleParallel(final Matrix m, final Function f, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		if (targetRowCount <= targetColumnCount) {
			return scaleParallelByRow(m, f, targetRowCount, target);
		}

		return scaleParallelByColumn(m, f, targetColumnCount, target);
	}

	private Matrix scaleParallelByColumn(final Matrix m, final Function f, final int targetColumnCount, final Matrix target) {
		List<ScalerByColumnTask> tasks = null;
		try {
			tasks = columnTaskPool.borrow(targetColumnCount);
			CountDownLatch taskGroup = new CountDownLatch(targetColumnCount);

			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				ScalerByColumnTask task = tasks.get(columnIndex);
				populateTask(task, taskGroup, m, f, target);
				task.setColumnIndex(columnIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(columnTaskPool, tasks);
		}
	}

	private Matrix scaleParallelByRow(final Matrix m, final Function f, final int targetRowCount, final Matrix target) {
		List<ScalerByRowTask> tasks = null;
		try {
			tasks = rowTaskPool.borrow(targetRowCount);
			CountDownLatch taskGroup = new CountDownLatch(targetRowCount);

			for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
				ScalerByRowTask task = tasks.get(rowIndex);
				populateTask(task, taskGroup, m, f, target);
				task.setRowIndex(rowIndex);
			}

			return runTasksAndGetTarget(tasks, target);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			recycle(rowTaskPool, tasks);
		}
	}

	private Matrix scaleSequential(final Matrix m, final Function f, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, f.evaluate(m.getValue(rowIndex, columnIndex)));
			}
		}

		return target;
	}

	@Override
	public void close() throws Exception {
		System.out.println("Closing Scaler " + toString() + "...");
		rowTaskPool.close();
		columnTaskPool.close();
		System.out.println("Closed Scaler " + toString());

	}

}
