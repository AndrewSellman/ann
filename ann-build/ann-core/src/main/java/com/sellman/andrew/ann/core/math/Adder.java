package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class Adder extends MathSupport {

	public Adder(final TaskService taskService) {
		super(taskService);
	}

	public Matrix add(final Matrix a, final Matrix b) {
		int rowCount = a.getRowCount();
		int columnCount = b.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		if (ParallelTaskGate.doMatrixTasksInParrallel(target.getCellCount())) {
			return addParallel(a, b, rowCount, columnCount, target);
		}

		return addSequential(a, b, rowCount, columnCount, target);
	}

	public Vector add(final Vector a, final Vector b) {
		return new Vector(add(a.getMatrix(), b.getMatrix()));
	}

	public double sum(final Matrix m) {
		int rowCount = m.getRowCount();
		int columnCount = m.getColumnCount();

		if (ParallelTaskGate.doMatrixTasksInParrallel(rowCount * columnCount)) {
			return sumParallel(m, rowCount, columnCount);
		}

		return sumSequential(m, rowCount, columnCount);
	}

	public double sum(final Vector v) {
		return sumSequential(v.getMatrix(), v.getRowCount(), 1);
	}

	private Matrix addParallel(final Matrix a, final Matrix b, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			return addParallelByRow(a, b, rowCount, target);
		}

		return addParallelByColumn(a, b, columnCount, target);
	}

	private Matrix addParallelByColumn(final Matrix a, final Matrix b, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getAddParallelByColumnTasks(a, b, columnCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getAddParallelByColumnTasks(final Matrix a, final Matrix b, final int columnCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new AdditionByColumnTask(taskGroup, a, b, columnIndex, target));
		}

		return tasks;
	}

	private Matrix addParallelByRow(final Matrix a, final Matrix b, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getAddParallelByRowTasks(a, b, rowCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getAddParallelByRowTasks(final Matrix a, final Matrix b, final int rowCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new AdditionByRowTask(taskGroup, a, b, rowIndex, target));
		}

		return tasks;
	}

	private Matrix addSequential(final Matrix a, final Matrix b, final int rowCount, final int columnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

	private double sumParallel(final Matrix m, final int rowCount, final int columnCount) {
		if (rowCount <= columnCount) {
			return sumParallelByRow(m, rowCount);
		}
		
		return sumParallelByColumn(m, columnCount);
	}

	private double sumParallelByColumn(final Matrix m, final int columnCount) {
		Vector target = new Vector(columnCount);
		List<AbstractTask> tasks = getSumParallelByColumnTasks(m, columnCount, target);
		return sum(runTasksAndReturnTarget(tasks, target));
	}

	private List<AbstractTask> getSumParallelByColumnTasks(final Matrix m, final int columnCount, Vector target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new SumByColumnTask(taskGroup, m, columnIndex, target));
		}

		return tasks;
	}

	private double sumParallelByRow(final Matrix m, final int rowCount) {
		Vector target = new Vector(rowCount);
		List<AbstractTask> tasks = getSumParallelByRowTasks(m, rowCount, target);
		return sum(runTasksAndReturnTarget(tasks, target));
	}

	private List<AbstractTask> getSumParallelByRowTasks(final Matrix m, final int rowCount, final Vector target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new SumByRowTask(taskGroup, m, rowIndex, target));
		}

		return tasks;
	}

	private double sumSequential(final Matrix m, final int rowCount, final int columnCount) {
		double result = 0;
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				result += m.getValue(rowIndex, columnIndex);
			}
		}

		return result;
	}

}
