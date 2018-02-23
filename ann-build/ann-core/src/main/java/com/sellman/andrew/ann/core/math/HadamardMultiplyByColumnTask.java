package com.sellman.andrew.ann.core.math;

class HadamardMultiplyByColumnTask extends AbstractMatrixByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixTarget().getRowCount(); rowIndex++) {
			double result = getMatrixA().getValue(rowIndex, getColumnIndex()) * getMatrixB().getValue(rowIndex, getColumnIndex());
			getMatrixTarget().setValue(rowIndex, getColumnIndex(), result);
		}
	}

}
