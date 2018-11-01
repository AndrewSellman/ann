package com.sellman.andrew.vann.core.training;

import java.util.List;

public abstract class TrainingBatchProvider {
	private final TrainingBatchFactory trainingBatchFactory;
	private final int batchSize;
	
	public TrainingBatchProvider(final TrainingBatchFactory trainingBatchFactory, final int batchSize) {
		this.trainingBatchFactory = trainingBatchFactory;
		this.batchSize = batchSize;
	}

	public abstract TrainingBatch getTrainingBatch(final TrainingProgress trainingProgress, final List<TrainingExample> allTrainingItems);
	
	public abstract int getBatchCount(final int totalTrainingItems);
	
	protected TrainingBatchFactory getTrainingBatchFactory() {
		return trainingBatchFactory;
	}

	protected int getBatchSize() {
		return batchSize;
	}

}
