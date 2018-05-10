package com.sellman.andrew.vann.core.math.add;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

class Addition extends AdvisableParallelizableOperation1<AdditionByRowTask, AdditionByColumnTask> {

	public Addition(final TaskService taskService, final AdditionByRowTaskPool opByRowTaskPool, final AdditionByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected final Matrix doSequentialOp(InspectableMatrix a, InspectableMatrix b, int targetRowCount, int targetColumnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, a.getValue(rowIndex, columnIndex) + b.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
