package com.sellman.andrew.ann.core.training.evaluator;

public class MaximumEpochsEvaluator extends TrainingEvaluator {
	int maximumEpochs;

	public MaximumEpochsEvaluator(int maximumEpochs) {
		this.maximumEpochs = maximumEpochs;
	}

	@Override
	public boolean isFinishedTraining() {
		return getTrainingProgress().getEpochIndex() == maximumEpochs;
	}

}
