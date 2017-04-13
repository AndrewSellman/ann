package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.Matrix;
import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MatrixMath {
	private TaskService taskService;

	public MatrixMath(TaskService taskService) {
		this.taskService = taskService;
	}

	public Matrix multiply(final Matrix left, final Matrix right) {
		int rowCount = left.getRowCount();
		int columnCount = right.getColumnCount();
		Matrix result = new Matrix(rowCount, columnCount);

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount * columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				tasks.add(new MultiplicationTask(left, rowIndex, right, columnIndex, result));
			}
		}

		taskService.runTasks(tasks);
		return result;
	}

}
