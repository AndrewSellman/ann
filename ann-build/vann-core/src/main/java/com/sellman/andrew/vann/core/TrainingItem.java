package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.math.Vector;

public class TrainingItem {
	private final Vector input;
	private final Vector expectedOutput;

	public TrainingItem(final Vector input, final Vector expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public Vector getInput() {
		return input;
	}

	public Vector getExpectedOutput() {
		return expectedOutput;
	}

}
