package com.sellman.andrew.vann.core.training;

import com.sellman.andrew.vann.core.math.RowVector;

public class TrainingExample {
	private final String[] rawInput;
	private final RowVector encodedInput;
	private final RowVector normalizedInput;
	private final RowVector expectedOutput;

	public TrainingExample(final String[] rawInput, final RowVector encodedInput, final RowVector normalizedInput, final RowVector expectedOutput) {
		this.rawInput = rawInput;
		this.encodedInput = encodedInput;
		this.normalizedInput = normalizedInput;
		this.expectedOutput = expectedOutput;
	}

	public RowVector getNormalizedInput() {
		return normalizedInput;
	}

	public RowVector getExpectedOutput() {
		return expectedOutput;
	}

	public String[] getRawInput() {
		return rawInput;
	}

	public RowVector getEncodedInput() {
		return encodedInput;
	}

}
