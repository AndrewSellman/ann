package com.sellman.andrew.ann.core.math.sum;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

class SummationByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		double result = 0;
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			result += getMatrixA().getValue(getRowIndex(), columnIndex);
		}

		getVectorTarget().setValue(getRowIndex(), result);
	}

}
