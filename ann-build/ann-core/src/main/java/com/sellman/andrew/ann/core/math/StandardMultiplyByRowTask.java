package com.sellman.andrew.ann.core.math;

class StandardMultiplyByRowTask extends AbstractMatrixByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixB().getColumnCount(); columnIndex++) {

			double result = 0;
			for (int k = 0; k < getMatrixA().getColumnCount(); k++) {
				result += getMatrixA().getValue(getRowIndex(), k) * getMatrixB().getValue(k, columnIndex);
			}

			getMatrixTarget().setValue(getRowIndex(), columnIndex, result);
		}
	}

}
