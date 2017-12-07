package com.sellman.andrew.ann.core.training.evaluator;

public class MinimumValidationErrorEvaluator extends TrainingEvaluator {
	double minimumError;

	public MinimumValidationErrorEvaluator(double minimumError) {
		this.minimumError = minimumError;
	}

	@Override
	public boolean isFinishedTraining() {
		return getTrainingProgress().getValidationError() <= minimumError;
	}

}
