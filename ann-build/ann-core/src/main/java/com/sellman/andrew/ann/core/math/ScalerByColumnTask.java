package com.sellman.andrew.ann.core.math;

class ScalerByColumnTask extends AbstractOperationByColumnTask {

	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {
			double scalar = getFunction().evaluate(getMatrixA().getValue(rowIndex, getColumnIndex()));
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), scalar);
		}
	}

}
