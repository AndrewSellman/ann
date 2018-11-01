package com.sellman.andrew.vann.core.training;

import java.util.List;

public class SplitTrainingExamples {
	private final List<TrainingExample> forTraining;
	private final List<TrainingExample> forValidation;

	public SplitTrainingExamples(final List<TrainingExample> forTraining, List<TrainingExample> forValidation) {
		this.forTraining = forTraining;
		this.forValidation = forValidation;
	}

	public List<TrainingExample> getForTraining() {
		return forTraining;
	}

	public List<TrainingExample> getForValidation() {
		return forValidation;
	}

}
