package com.sellman.andrew.ann.core.math;

class SummationByRowTask extends AbstractOperationByRowTask {

	@Override
	public void execute() {
		double result = 0;
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			result += getMatrixA().getValue(getRowIndex(), columnIndex);
		}

		getVectorTarget().setValue(getRowIndex(), result);
	}

}
