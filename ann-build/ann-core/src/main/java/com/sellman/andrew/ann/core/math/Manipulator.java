package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class Manipulator extends MathSupport {

	public Manipulator(final TaskService taskService) {
		super(taskService);
	}

	public Matrix transpose(final Matrix source) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		Matrix target = new Matrix(columnCount, rowCount);

		if (ParallelTaskGate.doMatrixTasksInParrallel(rowCount * columnCount)) {
			return transposeParallel(source, rowCount, columnCount, target);
		}

		return transposeSequential(source, rowCount, columnCount, target);
	}

	public Matrix transpose(final Vector source) {
		return transpose(source.getMatrix());
	}

	public void update(final Matrix source, final Matrix target) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();

		if (ParallelTaskGate.doMatrixTasksInParrallel(rowCount * columnCount)) {
			updateParallel(source, rowCount, columnCount, target);
			return;
		}

		updateSequential(source, target);
	}

	public void update(final Vector source, final Vector target) {
		update(source.getMatrix(), target.getMatrix());
	}

	private void updateParallel(final Matrix source, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			updateParallelByRow(source, rowCount, target);
			return;
		}

		updateParallelByColumn(source, columnCount, target);
		return;

	}

	private void updateParallelByColumn(final Matrix source, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getUpdateParallelByColumnTasks(source, columnCount, target);
		runTasks(tasks);
		return;
	}

	private List<AbstractTask> getUpdateParallelByColumnTasks(final Matrix source, final int columnCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new UpdateByRowTask(taskGroup, source, columnIndex, target));
		}

		return tasks;
	}

	private void updateParallelByRow(final Matrix source, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getUpdateParallelByRowTasks(source, rowCount, target);
		runTasks(tasks);
		return;
	}

	private List<AbstractTask> getUpdateParallelByRowTasks(final Matrix source, final int rowCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new UpdateByRowTask(taskGroup, source, rowIndex, target));
		}

		return tasks;
	}

	private void updateSequential(final Matrix source, final Matrix target) {
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
				target.setValue(rowIndex, columnIndex, source.getValue(rowIndex, columnIndex));
			}
		}
	}

	private Matrix transposeParallel(final Matrix source, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			return transposeByRow(source, rowCount, target);
		}

		return transposeByColumn(source, columnCount, target);
	}

	private Matrix transposeByColumn(final Matrix source, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getTransposeByColumnTasks(source, columnCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getTransposeByColumnTasks(final Matrix source, final int columnCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new TransposeByColumnTask(taskGroup, source, columnIndex, target));
		}

		return tasks;
	}

	private Matrix transposeByRow(final Matrix source, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getTransposeByRowTasks(source, rowCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getTransposeByRowTasks(final Matrix source, final int rowCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new TransposeByRowTask(taskGroup, source, rowIndex, target));
		}

		return tasks;
	}

	private Matrix transposeSequential(final Matrix source, final int rowCount, final int columnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				target.setValue(columnIndex, rowIndex, source.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
