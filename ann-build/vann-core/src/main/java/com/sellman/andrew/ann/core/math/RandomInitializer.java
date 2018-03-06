package com.sellman.andrew.ann.core.math;

import java.util.Random;

public class RandomInitializer {
	private static final Random RANDOM = new Random();

	public void init(Matrix m, double mininumValue, double maximumValue) {
		init(m, mininumValue, maximumValue, false);
	}

	public void init(Matrix m, double mininumValue, double maximumValue, boolean is0Allowed) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, getNextValue(mininumValue, maximumValue, is0Allowed));
			}
		}
	}

	private double getNextValue(double mininumValue, double maximumValue, boolean is0Allowed) {
		while (true) {
			double value = mininumValue + (maximumValue - mininumValue) * RANDOM.nextDouble();					
			if (is0Allowed || value != 0.0) {
				return value;
			}
		}
	}

}
