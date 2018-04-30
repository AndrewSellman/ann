package com.sellman.andrew.vann.core.math.multiply;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

class HadamardMultiplicationByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixTarget().getColumnCount(); columnIndex++) {
			double result = getMatrixA().getValue(getRowIndex(), columnIndex) * getMatrixB().getValue(getRowIndex(), columnIndex);
			getMatrixTarget().setValue(getRowIndex(), columnIndex, result);
		}
	}

}
