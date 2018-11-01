package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public class IncreasingValidationErrorRollbackEvaluator extends RollbackEvaluator {
	private final int consectiveValidationErrorIncreaseThresholdToRollback;
	private int consectiveValidationErrorIncreases;

	public IncreasingValidationErrorRollbackEvaluator(final int epochRollbackCount, final int consectiveValidationErrorIncreaseThresholdToRollback) {
		super(epochRollbackCount);
		this.consectiveValidationErrorIncreaseThresholdToRollback = consectiveValidationErrorIncreaseThresholdToRollback;
	}

	public boolean shouldRollback(final TrainingProgress trainingProgress) {
		if (trainingProgress.getValidationError() <= trainingProgress.getLastValidationError()) {
			return false;
		}

		consectiveValidationErrorIncreases++;
		if (consectiveValidationErrorIncreases == consectiveValidationErrorIncreaseThresholdToRollback) {
			consectiveValidationErrorIncreases = 0;
			return true;
		}

		return false;
	}

}
