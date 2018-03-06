package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public abstract class TrainingEvaluator {
	public final boolean isFinishedTraining(TrainingProgress progress) {
		return doIsFinishedTraining(progress);
	}
	
	protected abstract boolean doIsFinishedTraining(TrainingProgress progress);

}
