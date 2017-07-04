package com.sellman.andrew.ann.core.training;

public class TrainingStatistics {
	private int epoch;
	private int batch;
	private double epochMinimumError;
	private double epochMaximumError;
	private double epochTotalError;

	public int getEpoch() {
		return epoch;
	}

	public void incrementEpoch() {
		epoch++;
	}

	public int getBatch() {
		return batch;
	}

	public void incrementBatch() {
		batch++;
	}

	public void resetBatch() {
		batch = 0;
	}

	public double getEpochMinimumError() {
		return epochMinimumError;
	}

	public void setIfEpochMinimumError(double error) {
		if (error >= this.epochMinimumError) {
			return;
		}

		this.epochMinimumError = error;
	}

	public void resetEpochMinimumError() {
		this.epochMinimumError = 0.0;
	}

	public double getEpochMaximumError() {
		return epochMaximumError;
	}

	public void setIfEpochMaximumError(double error) {
		if (error <= this.epochMaximumError) {
			return;
		}

		this.epochMaximumError = error;
	}

	public void resetEpochMaximumError() {
		this.epochMinimumError = 1.0;
	}

	public double getEpochTotalError() {
		return epochTotalError;
	}

	public void addEpochTotalError(double error) {
		this.epochTotalError += error;
	}

	public void resetEpochTotalError() {
		this.epochTotalError = 0.0;
	}

}
