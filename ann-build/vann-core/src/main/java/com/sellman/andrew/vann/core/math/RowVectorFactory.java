package com.sellman.andrew.vann.core.math;

public class RowVectorFactory {

	public RowVector createFrom(Matrix source, int rowIndex) {
		return new RowVector(source.getRowValues(rowIndex));
	}

}
