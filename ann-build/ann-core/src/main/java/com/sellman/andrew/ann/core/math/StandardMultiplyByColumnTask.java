package com.sellman.andrew.ann.core.math;

class StandardMultiplyByColumnTask extends AbstractMatrixByColumnTask {

	@Override
	public void execute() {
		for (int rowIndex = 0; rowIndex < getMatrixA().getRowCount(); rowIndex++) {

			double result = 0;
			for (int k = 0; k < getMatrixA().getColumnCount(); k++) {
				result += getMatrixA().getValue(rowIndex, k) * getMatrixB().getValue(k, getColumnIndex());
			}

			getMatrixTarget().setValue(rowIndex, getColumnIndex(), result);
		}
	}

}
