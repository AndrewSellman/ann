package com.sellman.andrew.vann.core.math.update;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

class UpdationByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixSource().getRowCount(); rowIndex++) {
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), getMatrixSource().getValue(rowIndex, getColumnIndex()));
		}
	}

}
