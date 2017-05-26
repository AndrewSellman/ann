package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixSubtractor {
	private TaskService taskService;

	public MatrixSubtractor(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix subtract(final Matrix left, final Matrix right) {
		int rowCount = left.getRowCount();
		int columnCount = right.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixSubtractionTask(left, right, rowIndex, target));
		}

		taskService.runTasks(tasks);
		return target;
	}

	public Vector subtract(final Vector a, final Vector b) {
		Matrix result = subtract(a.getMatrix(), b.getMatrix());
		return new Vector(result);
	}

}
