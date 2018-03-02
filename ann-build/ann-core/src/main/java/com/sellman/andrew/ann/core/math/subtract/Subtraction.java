package com.sellman.andrew.ann.core.math.subtract;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;

class Subtraction extends AdvisableParallelizableOperation1<SubtractionByRowTask, SubtractionByColumnTask> {

	public Subtraction(final TaskService taskService, final SubtractionByRowTaskPool opByRowTaskPool, final SubtractionByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected Matrix doSequentialOp(Matrix left, Matrix right, int targetRowCount, int targetColumnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, left.getValue(rowIndex, columnIndex) - right.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

}
