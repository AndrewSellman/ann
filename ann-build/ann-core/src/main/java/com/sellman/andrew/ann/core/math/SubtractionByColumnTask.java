package com.sellman.andrew.ann.core.math;

class SubtractionByColumnTask extends AbstractOperationByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixLeft().getRowCount(); rowIndex++) {
			double result = getMatrixLeft().getValue(rowIndex, getColumnIndex()) - getMatrixRight().getValue(rowIndex, getColumnIndex());
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), result);
		}
	}

}
