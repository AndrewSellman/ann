package com.sellman.andrew.ann.core.math;

class TranspositionByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {
			double sourceValue = getMatrixA().getValue(rowIndex, getColumnIndex());
			getMatrixTarget().setValue(getColumnIndex(), rowIndex, sourceValue);
		}
	}

}
