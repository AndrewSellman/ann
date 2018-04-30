package com.sellman.andrew.vann.core.math.util;

import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.vann.core.math.Matrix;

public class RandomInitializer {
	private final boolean allow0;
	private final double minimumValue;
	private final double range;

	public RandomInitializer(double minimumValue, double maximumValue, boolean allow0) {
		this.minimumValue = minimumValue;
		this.range = maximumValue - minimumValue;
		this.allow0 = allow0;
	}

	public RandomInitializer(double minimumValue, double maximumValue) {
		this(minimumValue, maximumValue, false);
	}

	public void init(Matrix m) {
		if (allow0 || minimumValue > 0) {
			initWithZero(m);
		} else {
			initWithOutZero(m);
		}
	}

	private void initWithZero(Matrix m) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, getNextValue());
			}
		}
	}

	private void initWithOutZero(Matrix m) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, getNextNonZeroValue());
			}
		}
	}

	private double getNextValue() {
		return minimumValue + range * ThreadLocalRandom.current().nextDouble();
	}

	private double getNextNonZeroValue() {
		while (true) {
			double value = getNextValue();
			if (value != 0.0) {
				return value;
			}
		}
	}

}
