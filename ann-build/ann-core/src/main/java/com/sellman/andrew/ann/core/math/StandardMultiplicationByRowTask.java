package com.sellman.andrew.ann.core.math;

class StandardMultiplicationByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixRight().getColumnCount(); columnIndex++) {

			double result = 0;
			for (int k = 0; k < getMatrixLeft().getColumnCount(); k++) {
				result += getMatrixLeft().getValue(getRowIndex(), k) * getMatrixRight().getValue(k, columnIndex);
			}

			getMatrixTarget().setValue(getRowIndex(), columnIndex, result);
		}
	}

}
