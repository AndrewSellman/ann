package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class MinimumValidationErrorEvaluator extends TrainingEvaluator {
	double minimumError;

	public MinimumValidationErrorEvaluator(double minimumError) {
		this.minimumError = minimumError;
	}

	@Override
	protected boolean doIsFinishedTraining(TrainingProgress progress) {
		return progress.getValidationError() <= minimumError;
	}

}
