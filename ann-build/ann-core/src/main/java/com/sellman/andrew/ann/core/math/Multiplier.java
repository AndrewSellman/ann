package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class Multiplier extends MathSupport {

	public Multiplier(final TaskService taskService) {
		super(taskService);
	}

	public Matrix multiply(final Matrix left, final Matrix right) {
		int rowCount = left.getRowCount();
		int columnCount = right.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		if (ParallelTaskGate.doMatrixTasksInParrallel(target.getCellCount())) {
			return multiplyParallel(left, right, rowCount, columnCount, target);
		}

		return multiplySequential(left, right, rowCount, columnCount, target);
	}

	public Matrix hadamard(final Matrix a, final Matrix b) {
		int rowCount = a.getRowCount();
		int columnCount = a.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		if (ParallelTaskGate.doMatrixTasksInParrallel(target.getCellCount())) {
			return hadamardParallel(a, b, rowCount, columnCount, target);
		}

		return hadamardSequential(a, b, rowCount, columnCount, target);
	}

	public Vector multiply(final Matrix left, final Vector right) {
		return new Vector(multiply(left, right.getMatrix()));
	}

	public Matrix multiply(final Vector left, final Matrix right) {
		return (multiply(left.getMatrix(), right));
	}

	public Vector hadamard(final Vector a, final Vector b) {
		return new Vector(hadamard(a.getMatrix(), b.getMatrix()));
	}

	private Matrix hadamardParallel(final Matrix a, final Matrix b, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			return hadamardParallelByRow(a, b, rowCount, target);
		}

		return hadamardParallelByColumn(a, b, columnCount, target);
	}

	private Matrix hadamardParallelByRow(final Matrix a, final Matrix b, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getHadamardParallelByRowTasks(a, b, rowCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getHadamardParallelByRowTasks(final Matrix a, final Matrix b, final int rowCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new HadamardByRowTask(taskGroup, a, b, rowIndex, target));
		}

		return tasks;
	}

	private Matrix hadamardParallelByColumn(final Matrix a, final Matrix b, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getHadamardParallelByColumnTasks(a, b, columnCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getHadamardParallelByColumnTasks(final Matrix a, final Matrix b, final int columnCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new HadamardByColumnTask(taskGroup, a, b, columnIndex, target));
		}

		return tasks;
	}

	private Matrix hadamardSequential(final Matrix a, final Matrix b, final int rowCount, final int columnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

	private Matrix multiplyParallel(final Matrix left, final Matrix right, final int rowCount, final int columnCount, final Matrix target) {
		int taskCount = rowCount * columnCount;
		CountDownLatch taskGroup = new CountDownLatch(taskCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>();
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				tasks.add(new MultiplicationTask(taskGroup, left, rowIndex, right, columnIndex, target));
			}
		}

		return runTasksAndReturnTarget(tasks, target);
	}

	private Matrix multiplySequential(final Matrix left, final Matrix right, final int rowCount, final int columnCount, final Matrix target) {
		double result;
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

				result = 0;
				for (int k = 0; k < left.getColumnCount(); k++) {
					result += left.getValue(rowIndex, k) * right.getValue(k, columnIndex);
				}
				target.setValue(rowIndex, columnIndex, result);

			}
		}

		return target;
	}

}
