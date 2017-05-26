package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixManipulator {
	private TaskService taskService;

	public MatrixManipulator(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix transpose(final Matrix source) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		Matrix transposedTarget = new Matrix(columnCount, rowCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixTransposeTask(source, rowIndex, transposedTarget));
		}

		taskService.runTasks(tasks);
		return transposedTarget;
	}

}
