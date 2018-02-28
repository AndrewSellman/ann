package com.sellman.andrew.ann.core.math;

class UpdationByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixSource().getRowCount(); rowIndex++) {
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), getMatrixSource().getValue(rowIndex, getColumnIndex()));
		}
	}

}
