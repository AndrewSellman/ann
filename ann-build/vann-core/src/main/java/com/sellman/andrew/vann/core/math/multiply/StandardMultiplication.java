package com.sellman.andrew.vann.core.math.multiply;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

class StandardMultiplication extends AdvisableParallelizableOperation1<StandardMultiplicationByRowTask, StandardMultiplicationByColumnTask> {

	public StandardMultiplication(final TaskService taskService, final StandardMultiplicationByRowTaskPool opByRowTaskPool, final StandardMultiplicationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected Matrix doSequentialOp(final InspectableMatrix left, final InspectableMatrix right, final int targetRowCount, final int targetColumnCount, final Matrix target) {
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
