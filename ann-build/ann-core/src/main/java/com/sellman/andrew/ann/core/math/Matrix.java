package com.sellman.andrew.ann.core.math;

public class Matrix {
	private final double[][] data;
	
	public Matrix(double[][] data) {
		this.data = data;
	}
	
	public Matrix(int rowCount, int columnCount) {
		this.data = new double[rowCount][columnCount];
	}

	public int getRowCount() {
		return data.length;
	}
	
	public int getColumnCount() {
		return data[0].length;
	}

	public double getValue(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	public void setValue(int rowIndex, int columnIndex, double value) {
		data[rowIndex][columnIndex] = value;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < getColumnCount(); c++) {
				sb.append(getValue(r, c));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}