package com.sellman.andrew.vann.core.training.evaluator;

import com.sellman.andrew.vann.core.training.TrainingProgress;

public abstract class TrainingEvaluator {
	public final boolean isFinishedTraining(TrainingProgress progress) {
		return doIsFinishedTraining(progress);
	}
	
	protected abstract boolean doIsFinishedTraining(TrainingProgress progress);

}
