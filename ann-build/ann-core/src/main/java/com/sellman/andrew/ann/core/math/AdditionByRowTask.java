package com.sellman.andrew.ann.core.math;

class AdditionByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			double sum = getMatrixA().getValue(getRowIndex(), columnIndex) + getMatrixB().getValue(getRowIndex(), columnIndex);
			getMatrixTarget().setValue(getRowIndex(), columnIndex, sum);
		}
	}

}
