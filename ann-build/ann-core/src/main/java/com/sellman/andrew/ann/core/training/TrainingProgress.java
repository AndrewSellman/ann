package com.sellman.andrew.ann.core.training;

public class TrainingProgress {
	private TrainingStatistics statistics;
	private double accumulatedBatchErrors;
	private double epochError;

	public TrainingStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(TrainingStatistics statistics) {
		this.statistics = statistics;
	}

	public double getEpochError() {
		return epochError;
	}

	public void setEpochError(double epochError) {
		this.epochError = epochError;
	}

	public void resetAccumulatedBatchErrors() {
		accumulatedBatchErrors = 0;
	}

	public double getAccumulatedBatchErrors() {
		return accumulatedBatchErrors;
	}

	public void addToAccumulatedBatchErrors(double batchError) {
		this.accumulatedBatchErrors += batchError;
	}
	
}
