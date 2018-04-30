package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class MinimumEpochErrorEvaluator extends TrainingEvaluator {
	double minimumError;

	public MinimumEpochErrorEvaluator(double minimumError) {
		this.minimumError = minimumError;
	}

	@Override
	protected boolean doIsFinishedTraining(TrainingProgress progress) {
		return progress.getEpochError() <= minimumError;
	}

}
