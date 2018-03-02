package com.sellman.andrew.ann.core.math.update;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

class UpdationByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixSource().getColumnCount(); columnIndex++) {
			getMatrixTarget().setValue(getRowIndex(), columnIndex, getMatrixSource().getValue(getRowIndex(), columnIndex));
		}
	}

}
