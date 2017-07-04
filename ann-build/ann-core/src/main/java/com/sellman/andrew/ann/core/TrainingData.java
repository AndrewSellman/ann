package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.Matrix;

public class TrainingData {
	private final Matrix input;
	private final Matrix expectedOutput;

	public TrainingData(final Matrix input, final Matrix expectedOutput) {
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
