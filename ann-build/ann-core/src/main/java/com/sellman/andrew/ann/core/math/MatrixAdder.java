package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixAdder {
	private TaskService taskService;

	public MatrixAdder(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix add(final Matrix a, final Matrix b) {
		int rowCount = a.getRowCount();
		int columnCount = b.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixAdditionTask(a, b, rowIndex, target));
		}

		taskService.runTasks(tasks);
		return target;
	}

	public Vector add(final Vector a, final Vector b) {
		Matrix result = add(a.getMatrix(), b.getMatrix());
		return new Vector(result);
	}

}
