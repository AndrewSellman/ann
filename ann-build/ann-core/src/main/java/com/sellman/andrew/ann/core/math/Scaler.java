package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskService;

class Scaler extends ParallelizableOperation {

	public Scaler(final TaskService taskService, final ParallelizableOperationAdvisor advisor) {
		super(taskService, advisor);
	}

	public Matrix scale(final Matrix source, final Function f) {
		int rowCount = source.getRowCount();
		int columnCount = source.getColumnCount();
		Matrix target = new Matrix(rowCount, columnCount);
//TODO
//		if (ParallelizableOperationAdvisor.shouldMultiplyInParrallel(rowCount * columnCount)) {
//			return scaleSequential(source, f, rowCount, columnCount, target);
//		}

		return scaleParallel(source, f, rowCount, columnCount, target);
	}

	public Vector scale(final Vector v, final Function f) {
		return new Vector(scale(v.getMatrix(), f));
	}

	private Matrix scaleParallel(final Matrix source, final Function f, final int rowCount, final int columnCount, final Matrix target) {
		if (rowCount <= columnCount) {
			return scaleParallelByRow(source, f, rowCount, target);
		}

		return scaleParallelByColumn(source, f, columnCount, target);
	}

	private Matrix scaleParallelByColumn(final Matrix source, final Function f, final int columnCount, final Matrix target) {
		List<AbstractTask> tasks = getScaleParallelByColumnTasks(source, f, columnCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getScaleParallelByColumnTasks(final Matrix source, final Function f, final int columnCount, final Matrix target) {
		CountDownLatch groupTask = new CountDownLatch(columnCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(columnCount);
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			tasks.add(new ScaleByColumnTask(groupTask, source, columnIndex, f, target));
		}

		return tasks;
	}

	private Matrix scaleParallelByRow(final Matrix source, final Function f, final int rowCount, final Matrix target) {
		List<AbstractTask> tasks = getScaleParallelByRowTasks(source, f, rowCount, target);
		return runTasksAndReturnTarget(tasks, target);
	}

	private List<AbstractTask> getScaleParallelByRowTasks(final Matrix source, final Function f, final int rowCount, final Matrix target) {
		CountDownLatch groupTask = new CountDownLatch(rowCount);
		List<AbstractTask> tasks = new ArrayList<AbstractTask>(rowCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			tasks.add(new ScaleByRowTask(groupTask, source, rowIndex, f, target));
		}

		return tasks;
	}

	
	private Matrix scaleSequential(final Matrix source, final Function f, final int rowCount, final int columnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < source.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < source.getColumnCount(); columnIndex++) {
				target.setValue(rowIndex, columnIndex, f.evaluate(source.getValue(rowIndex, columnIndex)));
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
