package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public class MaximumEpochsEvaluator implements TrainingEvaluator {
	int maximumEpochs;

	public MaximumEpochsEvaluator(int maximumEpochs) {
		this.maximumEpochs = maximumEpochs;
	}

	@Override
	public boolean isFinishedTraining(TrainingProgress progress) {
		return maximumEpochs == progress.getStatistics().getEpoch();
	}

}
