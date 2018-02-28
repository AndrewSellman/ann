package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class HadamardMultiplication extends ParallelizableOperation1<HadamardMultiplicationByRowTask, HadamardMultiplicationByColumnTask> {

	public HadamardMultiplication(final TaskService taskService, final HadamardMultiplicationByRowTaskPool opByRowTaskPool, final HadamardMultiplicationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected Matrix doSequentialOp(Matrix a, Matrix b, int targetRowCount, int targetColumnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
