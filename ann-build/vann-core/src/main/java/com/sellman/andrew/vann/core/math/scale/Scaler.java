package com.sellman.andrew.vann.core.math.scale;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation2;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.function.Function;

class Scaler extends AdvisableParallelizableOperation2<ScalerByRowTask, ScalerByColumnTask> {

	public Scaler(final TaskService taskService, final ScalerByRowTaskPool opByRowTaskPool, final ScalerByColumnTaskPool opByColumnTaskPool, final ParallelizableOperation2Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool, advisor);
	}

	@Override
	protected Matrix doSequentialOp(final Matrix m, final Function f, final int targetRowCount, final int targetColumnCount, final Matrix target) {
		for (int rowIndex = 0; rowIndex < targetRowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < targetColumnCount; columnIndex++) {
				target.setValue(rowIndex, columnIndex, f.evaluate(m.getValue(rowIndex, columnIndex)));
			}
		}

		return target;
	}

}
