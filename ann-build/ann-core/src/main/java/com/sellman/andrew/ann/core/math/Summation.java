package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;

class Summation extends ParallelizableOperation4<SummationByRowTask, SummationByColumnTask> {

	public Summation(final TaskService taskService, final SummationByRowTaskPool opByRowTaskPool, final SummationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation4Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected double doSequentialOp(Matrix m, int targetRowCount, int targetColumnCount, Matrix target) {
		double result = 0;
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				result += m.getValue(rowIndex, columnIndex);
			}
		}

		return result;
	}


}
