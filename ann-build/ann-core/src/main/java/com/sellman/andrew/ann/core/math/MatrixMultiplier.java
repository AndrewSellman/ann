package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixMultiplier {
	private TaskService taskService;

	public MatrixMultiplier(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix multiply(final Matrix left, final Matrix right) {
		int rowCount = left.getRowCount();
		int columnCount = right.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				tasks.add(new MatrixMultiplicationTask(left, rowIndex, right, columnIndex, target));
			}
		}

		taskService.runTasks(tasks);
		return target;
	}

	public Matrix hadamard(Matrix a, Matrix b) {
		int rowCount = a.getRowCount();
		Matrix target = new Matrix(rowCount, a.getColumnCount());
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new HadamardTask(a, b, rowIndex, target));
		}
		taskService.runTasks(tasks);
		return target;
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

}
