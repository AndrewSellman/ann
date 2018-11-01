package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class RollbackEvaluator {
	private final int epochRollbackCount;
	
	public RollbackEvaluator(final int epochRollbackCount) {
		this.epochRollbackCount = epochRollbackCount;
	}
	
	public int getEpochRollbackCount() {
		return epochRollbackCount;
	}

	public abstract boolean shouldRollback(final TrainingProgress trainingProgress);
	
}
