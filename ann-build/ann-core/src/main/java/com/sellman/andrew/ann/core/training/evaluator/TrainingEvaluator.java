package com.sellman.andrew.ann.core.training.evaluator;

import java.util.concurrent.atomic.AtomicBoolean;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.training.TrainingProgress;

public abstract class TrainingEvaluator extends AbstractTask {
	private AtomicBoolean finishedTraining;
	private TrainingProgress progress;

	@Override
	public final void execute() {
		finishedTraining.compareAndSet(false, isFinishedTraining());
	}

	protected abstract boolean isFinishedTraining();

	public final void initFinishedTraining(AtomicBoolean finishedTraining) {
		this.finishedTraining = finishedTraining;
	}

	public final void initTrainingProgress(TrainingProgress progress) {
		this.progress = progress;
	}

	protected final TrainingProgress getTrainingProgress() {
		return progress;
	}

}
