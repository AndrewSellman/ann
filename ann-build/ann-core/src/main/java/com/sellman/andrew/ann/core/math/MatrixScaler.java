package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class MatrixScaler {
	private TaskService taskService;

	public MatrixScaler(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix scale(final Matrix m, final Function f) {
		int rowCount = m.getRowCount();
		int columnCount = m.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				tasks.add(new MatrixScalerTask(m, rowIndex, columnIndex, f, target));
			}
		}

		taskService.runTasks(tasks);
		return target;
	}

	public Vector scale(final Vector v, final Function f) {
		return new Vector(scale(v.getMatrix(), f));
	}


}
