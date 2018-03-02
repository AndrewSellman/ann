package com.sellman.andrew.ann.core.math.transpose;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation3;

class Transposition extends AdvisableParallelizableOperation3<TranspositionByRowTask, TranspositionByColumnTask> {

	public Transposition(final TaskService taskService, final TranspositionByRowTaskPool opByRowTaskPool, TranspositionByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation3Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected final Matrix doSequentialOp(final Matrix source, final int sourceRowCount, final int sourceColumnCount) {
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
