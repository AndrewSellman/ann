package com.sellman.andrew.vann.core.math.scale;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

class ScalerByRowTask extends AbstractOperationByRowTask {

	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			double scalar = getFunction().evaluate(getMatrixA().getValue(getRowIndex(), columnIndex));
			getMatrixTarget().setValue(getRowIndex(), columnIndex, scalar);
		}
	}

}
