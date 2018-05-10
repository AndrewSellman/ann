package com.sellman.andrew.vann.core.math.multiply;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

class HadamardMultiplication extends AdvisableParallelizableOperation1<HadamardMultiplicationByRowTask, HadamardMultiplicationByColumnTask> {

	public HadamardMultiplication(final TaskService taskService, final HadamardMultiplicationByRowTaskPool opByRowTaskPool, final HadamardMultiplicationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected final Matrix doSequentialOp(InspectableMatrix a, InspectableMatrix b, int targetRowCount, int targetColumnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) * b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
