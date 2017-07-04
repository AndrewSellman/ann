package com.sellman.andrew.ann.core.training.evaluator;

import com.sellman.andrew.ann.core.training.TrainingProgress;

public interface TrainingEvaluator {

	public boolean isFinishedTraining(TrainingProgress progress);
	
}
