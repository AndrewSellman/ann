package com.sellman.andrew.ann.core.math.update;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation5;

class Updation extends AdvisableParallelizableOperation5<UpdationByRowTask, UpdationByColumnTask> {

	public Updation(final TaskService taskService, final UpdationByRowTaskPool opByRowTaskPool, final UpdationByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation5Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected void doSequentialOp(Matrix source, int rowCount, int columnCount, Matrix target) {
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				target.setValue(columnIndex, rowIndex, source.getValue(rowIndex, columnIndex));
			}
		}
	}

}
