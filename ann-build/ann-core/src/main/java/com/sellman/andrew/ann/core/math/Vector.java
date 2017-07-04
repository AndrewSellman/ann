package com.sellman.andrew.ann.core.math;

public class Vector {
	private static final int COLUMN_COUNT = 1;
	private static final int COLUMN_INDEX = 0;
	private final Matrix matrix;

	public Vector(double[] data) {
		this(data.length);
		for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
			setValue(rowIndex, data[rowIndex]);
		}
	}

	public Vector(int rowCount) {
		matrix = new Matrix(rowCount, COLUMN_COUNT);
	}

	protected Vector(Matrix matrix) {
		this.matrix = matrix;
	}

	public int getRowCount() {
		return matrix.getRowCount();
	}

	public double getValue(int rowIndex) {
		return matrix.getValue(rowIndex, COLUMN_INDEX);
	}

	public void setValue(int rowIndex, double value) {
		matrix.setValue(rowIndex, COLUMN_INDEX, value);
	}

	protected Matrix getMatrix() {
		return matrix;
	}

	@Override
	public String toString() {
		return matrix.toString();
	}

}
