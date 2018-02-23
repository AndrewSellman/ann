package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class Subtractor extends ParallelizableOperation {

	public Subtractor(final TaskService taskService, final ParallelizableOperationAdvisor advisor) {
		super(taskService, advisor);
	}

	public Matrix subtract(final Matrix left, final Matrix right) {
		int rowCount = left.getRowCount();
		int columnCount = right.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);
//TODO
//		if (ParallelizableOperationAdvisor.shouldMultiplyInParrallel(rowCount * columnCount)) {
//			return subtractParallel(left, right, rowCount, columnCount, target);
//		}

		return subtractSequential(left, right, rowCount, columnCount, target);
	}

	public Vector subtract(final Vector left, final Vector right) {
		return new Vector(subtract(left.getMatrix(), right.getMatrix()));
	}

	private Matrix subtractParallel(final Matrix left, final Matrix right, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			return subtractParallelByRow(left, right, rowCount, target);
		}

		return subtractParallelByColumn(left, right, columnCount, target);
	}

	private Matrix subtractParallelByColumn(final Matrix left, final Matrix right, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getSubtractParallelByColumnTasks(left, right, columnCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getSubtractParallelByColumnTasks(final Matrix left, final Matrix right, final int columnCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new SubtractionByColumnTask(taskGroup, left, right, columnIndex, target));
		}

		return tasks;
	}

	private Matrix subtractParallelByRow(final Matrix left, final Matrix right, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getSubtractParallelByRowTasks(left, right, rowCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getSubtractParallelByRowTasks(final Matrix left, final Matrix right, final int rowCount, final Matrix target) {
		CountDownLatch taskGroup = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new SubtractionByRowTask(taskGroup, left, right, rowIndex, target));
		}

		return tasks;
	}

	private Matrix subtractSequential(final Matrix left, final Matrix right, final int rowCount, final int columnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, left.getValue(rowIndex, columnIndex) - right.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

	@Override
	protected Matrix doParallelOp(Matrix left, Matrix right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Matrix doSequentialOp(Matrix left, Matrix right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
