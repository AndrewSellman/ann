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

	public Matrix transpose(final Vector source) {
		return transpose(source.getMatrix());
	}

	public void update(final Matrix source, final Matrix target) {
		int rowCount = source.getRowCount();
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixUpdateTask(source, target, rowIndex));
		}

		taskService.runTasks(tasks);
	}
	
	public void update(final Vector source, final Vector target) {
		update(source.getMatrix(), target.getMatrix());
	}

}
