package com.sellman.andrew.vann.core.training;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class TrainingBatch {
	private final InspectableMatrix input;
	private final InspectableMatrix expectedOutput;

	public TrainingBatch(final InspectableMatrix input, final InspectableMatrix expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public InspectableMatrix getInput() {
		return input;
	}

	public InspectableMatrix getExpectedOutput() {
		return expectedOutput;
	}

}
