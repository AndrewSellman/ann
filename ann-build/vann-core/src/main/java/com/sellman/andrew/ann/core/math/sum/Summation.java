package com.sellman.andrew.ann.core.math.sum;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation4;

class Summation extends AdvisableParallelizableOperation4<SummationByRowTask, SummationByColumnTask> {

	public Summation(final TaskService taskService, final SummationByRowTaskPool opByRowTaskPool, final SummationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation4Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected double doSequentialOp(Matrix m, int targetRowCount, int targetColumnCount) {
		double result = 0;
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				result += m.getValue(rowIndex, columnIndex);
			}
		}

		return result;
	}

}
