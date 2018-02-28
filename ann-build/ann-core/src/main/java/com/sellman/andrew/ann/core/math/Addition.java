package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class Addition extends ParallelizableOperation1<AdditionByRowTask, AdditionByColumnTask> {

	public Addition(final TaskService taskService, final AdditionByRowTaskPool opByRowTaskPool, final AdditionByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected final Matrix doSequentialOp(Matrix a, Matrix b, int targetRowCount, int targetColumnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
