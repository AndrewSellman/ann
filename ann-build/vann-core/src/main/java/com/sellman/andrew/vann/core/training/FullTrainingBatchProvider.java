package com.sellman.andrew.vann.core.training;

import java.util.List;

public class FullTrainingBatchProvider extends TrainingBatchProvider {
	private TrainingBatch batch;

	public FullTrainingBatchProvider(final TrainingBatchFactory trainingBatchFactory) {
		super(trainingBatchFactory, 0);
	}

	@Override
	public TrainingBatch getTrainingBatch(TrainingProgress trainingProgress, List<TrainingExample> allTrainingItems) {
		if (batch != null) {
			return batch;
		}

		batch = getTrainingBatchFactory().createFor(allTrainingItems);
		return batch;
	}

	@Override
	public int getBatchCount(int trainingItemsCount) {
		return 1;
	}

}
