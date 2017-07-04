package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixAverager {
	private TaskService taskService;

	public MatrixAverager(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix average(final Matrix a) {
		int columnCount = a.getColumnCount();
		Matrix target = new Matrix(1, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new MatrixAverageTask(a, columnIndex, target));
		}

		taskService.runTasks(tasks);
		return target;
	}

}
