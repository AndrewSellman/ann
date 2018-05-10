package com.sellman.andrew.vann.core.math.transpose;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation3;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;

class Transposition extends AdvisableParallelizableOperation3<TranspositionByRowTask, TranspositionByColumnTask> {

	public Transposition(final TaskService taskService, final TranspositionByRowTaskPool opByRowTaskPool, TranspositionByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation3Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected final Matrix doSequentialOp(final InspectableMatrix source, final int sourceRowCount, final int sourceColumnCount) {
		Matrix target = getTarget(sourceRowCount, sourceColumnCount);

		for (int rowIndex = 0; rowIndex < sourceRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < sourceColumnCount; columnIndex++) {
				target.setValue(columnIndex, rowIndex, source.getValue(rowIndex, columnIndex));
			}
		}

		return target;
	}

	@Override
	protected final Matrix getTarget(int sourceRowCount, int sourceColumnCount) {
		return new Matrix(sourceColumnCount, sourceRowCount);
	}

}
