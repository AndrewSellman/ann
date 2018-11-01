package com.sellman.andrew.vann.core.training;

public class StochasticTrainingBatchProvider extends PartialTrainingBatchProvider {

	public StochasticTrainingBatchProvider(final TrainingBatchFactory trainingBatchFactory) {
		super(trainingBatchFactory, 1);
	}
	
	@Override
	public int getBatchCount(int totalTrainingItems) {
		return totalTrainingItems;
	}

}
