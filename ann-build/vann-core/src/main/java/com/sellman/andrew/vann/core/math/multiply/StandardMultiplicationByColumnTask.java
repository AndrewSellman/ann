package com.sellman.andrew.vann.core.math.multiply;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

class StandardMultiplicationByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixLeft().getRowCount(); rowIndex++) {

			double result = 0;
			for (int k = 0; k < getMatrixLeft().getColumnCount(); k++) {
				result += getMatrixLeft().getValue(rowIndex, k) * getMatrixRight().getValue(k, getColumnIndex());
			}

			getMatrixTarget().setValue(rowIndex, getColumnIndex(), result);
		}
	}

}
