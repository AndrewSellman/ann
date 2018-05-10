package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.math.RowVector;

public class TrainingItem {
	private final RowVector input;
	private final RowVector expectedOutput;

	public TrainingItem(final RowVector input, final RowVector expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public RowVector getInput() {
		return input;
	}

	public RowVector getExpectedOutput() {
		return expectedOutput;
	}

}
