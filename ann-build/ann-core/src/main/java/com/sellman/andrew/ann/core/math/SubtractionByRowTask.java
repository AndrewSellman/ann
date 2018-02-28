package com.sellman.andrew.ann.core.math;

class SubtractionByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixLeft().getColumnCount(); columnIndex++) {
			double result = getMatrixLeft().getValue(getRowIndex(), columnIndex) - getMatrixRight().getValue(getRowIndex(), columnIndex);
			getMatrixTarget().setValue(getRowIndex(), columnIndex, result);
		}
	}

}
