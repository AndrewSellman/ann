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

		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixAdditionTask(a, b, rowIndex, target));
		}

		taskService.runTasks(tasks);
		return target;
	}
	
	public Vector add(final Vector a, final Vector b) {
		return new Vector(add(a.getMatrix(), b.getMatrix()));
	}

	public double sum(final Matrix m) {
		int rowCount = m.getRowCount();
		Vector target = new Vector(rowCount);
		
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new MatrixSummationTask(m, rowIndex, target));
		}

		taskService.runTasks(tasks);		
		return sum(target);
		
	}

	public double sum(Vector v) {
		double result = 0;
		for (int rowIndex = 0; rowIndex < v.getRowCount(); rowIndex++) {
			result += v.getValue(rowIndex);
		}
			
		return result;
	}

}
