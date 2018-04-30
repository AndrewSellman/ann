package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class MaximumEpochsEvaluator extends TrainingEvaluator {
	int maximumEpochs;

	public MaximumEpochsEvaluator(int maximumEpochs) {
		this.maximumEpochs = maximumEpochs;
	}

	@Override
	protected boolean doIsFinishedTraining(TrainingProgress progress) {
		return progress.getEpochIndex() + 1 >= maximumEpochs;
	}

}
