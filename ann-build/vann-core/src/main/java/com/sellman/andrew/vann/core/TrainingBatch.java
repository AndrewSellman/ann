package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.math.Matrix;

public class TrainingBatch {
	private final Matrix input;
	private final Matrix expectedOutput;

	public TrainingBatch(final Matrix input, final Matrix expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public Matrix getInput() {
		return input;
	}

	public Matrix getExpectedOutput() {
		return expectedOutput;
	}

}
