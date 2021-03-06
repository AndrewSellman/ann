package com.sellman.andrew.vann.core.math.scale;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

class ScalerByColumnTask extends AbstractOperationByColumnTask {

	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {
			double scalar = getFunction().evaluate(getMatrixA().getValue(rowIndex, getColumnIndex()));
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), scalar);
		}
	}

}
