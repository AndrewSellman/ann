package com.sellman.andrew.ann.core.math.multiply;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;

class StandardMultiplication extends AdvisableParallelizableOperation1<StandardMultiplicationByRowTask, StandardMultiplicationByColumnTask> {

	public StandardMultiplication(final TaskService taskService, final StandardMultiplicationByRowTaskPool opByRowTaskPool, final StandardMultiplicationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected Matrix doSequentialOp(final Matrix left, final Matrix right, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {

				double result = 0;
				for (int k = 0; k < left.getColumnCount(); k++) {
					result += left.getValue(rowIndex, k) * right.getValue(k, columnIndex);
				}

				target.setValue(rowIndex, columnIndex, result);
			}
		}

		return target;
	}

}
