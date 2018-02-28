package com.sellman.andrew.ann.core.math;

class ScalerByRowTask extends AbstractOperationByRowTask {

	public void execute() {
		for (int columnIndex = 0; columnIndex < getMatrixA().getColumnCount(); columnIndex++) {
			double scalar = getFunction().evaluate(getMatrixA().getValue(getRowIndex(), columnIndex));
			getMatrixTarget().setValue(getRowIndex(), columnIndex, scalar);
		}
	}

}
