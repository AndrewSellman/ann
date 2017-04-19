package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MatrixScaler {
	private TaskService taskService;

	public MatrixScaler(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix scale(final Matrix source, final Function function) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				tasks.add(new MatrixScalerTask(source, rowIndex, columnIndex, function, target));
			}
		}

		taskService.runTasks(tasks);
		return target;
	}
	
	public Vector scale(final Vector source, final Function function) {
		Matrix result = scale(source.getMatrix(), function);
		return new Vector(result);
	}

}
