package com.sellman.andrew.vann.core.math.transpose;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

class TranspositionByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			double sourceValue = getMatrixA().getValue(getRowIndex(), columnIndex);
			getMatrixTarget().setValue(columnIndex, getRowIndex(), sourceValue);
		}
	}

}
