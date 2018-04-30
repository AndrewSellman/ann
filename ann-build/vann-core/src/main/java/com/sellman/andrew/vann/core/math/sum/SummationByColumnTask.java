package com.sellman.andrew.vann.core.math.sum;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

class SummationByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		double result = 0;
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {
			result += getMatrixA().getValue(rowIndex, getColumnIndex());
		}

		getVectorTarget().setValue(getColumnIndex(), result);
	}

}
