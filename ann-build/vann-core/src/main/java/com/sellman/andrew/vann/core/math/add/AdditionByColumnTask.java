package com.sellman.andrew.vann.core.math.add;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

class AdditionByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {
			double sum = getMatrixA().getValue(rowIndex, getColumnIndex()) + getMatrixB().getValue(rowIndex, getColumnIndex());
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), sum);
		}
	}

}
