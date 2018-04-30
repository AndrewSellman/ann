package com.sellman.andrew.vann.core.math.sum;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation4;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation4Advisor;

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
