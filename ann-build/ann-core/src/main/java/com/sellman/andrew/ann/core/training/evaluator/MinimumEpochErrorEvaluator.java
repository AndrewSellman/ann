package com.sellman.andrew.ann.core.training.evaluator;

public class MinimumEpochErrorEvaluator extends TrainingEvaluator {
	double minimumError;

	public MinimumEpochErrorEvaluator(double minimumError) {
		this.minimumError = minimumError;
	}

	@Override
	public boolean isFinishedTraining() {
		return getTrainingProgress().getEpochError() <= minimumError;
	}

}
