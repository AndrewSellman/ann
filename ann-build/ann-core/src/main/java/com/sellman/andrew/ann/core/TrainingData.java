package com.sellman.andrew.ann.core;

import com.sellman.andrew.ann.core.math.Vector;

public class TrainingData {
	private final Vector input;
	private final Vector expectedOutput;
	
	public TrainingData(final Vector input, final Vector expectedOutput) {
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
