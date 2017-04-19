package com.sellman.andrew.ann.core.math;

public class Vector {
	private static final int ROW_COUNT = 1;
	private static final int ROW_INDEX = 0;
	private final Matrix matrix;

	public Vector(double[] data) {
		this(data.length);
		for (int c = 0; c < data.length; c++) {
			setValue(c, data[c]);
		}
	}

	public Vector(int columnCount) {
		matrix = new Matrix(ROW_COUNT, columnCount);
	}

	protected Vector(Matrix matrix) {
		this.matrix = matrix;
	}

	public int getColumnCount() {
		return matrix.getColumnCount();
	}

	public double getValue(int columnIndex) {
		return matrix.getValue(ROW_INDEX, columnIndex);
	}

	public void setValue(int columnIndex, double value) {
		matrix.setValue(ROW_INDEX, columnIndex, value);
	}

	protected Matrix getMatrix() {
		return matrix;
	}

	@Override
	public String toString() {
		return matrix.toString();
	}

}
