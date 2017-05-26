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

	public Vector multiply(final Vector left, final Matrix right) {
		Matrix result = multiply(left.getMatrix(), right);
		return new Vector(result);
	}
	
	public Matrix multiply(Matrix left, Vector right) {
		return multiply(left, right.getMatrix());
	}

	public Vector hadamard(Vector a, Vector b) {
		int columnCount = a.getColumnCount();
		Vector target = new Vector(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new HadamardTask(a, b, columnIndex, target));
		}
		taskService.runTasks(tasks);
		return target;
	}

}
