package com.sellman.andrew.vann.core.training;

import java.util.List;

public class PartialTrainingBatchProvider extends TrainingBatchProvider {

	public PartialTrainingBatchProvider(final TrainingBatchFactory trainingBatchFactory, final int batchSize) {
		super(trainingBatchFactory, batchSize);
	}

	@Override
	public TrainingBatch getTrainingBatch(TrainingProgress trainingProgress, List<TrainingExample> allTrainingData) {
		int fromIndex = trainingProgress.getBatchIndex() * getBatchSize();
		int toIndex = fromIndex + getBatchSize();
		if (toIndex > allTrainingData.size()) {
			toIndex = allTrainingData.size();
		}

		List<TrainingExample> batchTrainingData = allTrainingData.subList(fromIndex, toIndex);
		return getTrainingBatchFactory().createFor(batchTrainingData);
	}

	@Override
	public int getBatchCount(int totalTrainingItems) {
		int batchCount = totalTrainingItems / getBatchSize();
		if (totalTrainingItems % getBatchSize() > 0) {
			batchCount++;
		}

		return batchCount;
	}

}
