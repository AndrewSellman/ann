package com.sellman.andrew.ann.core.event;

public class BatchChangeEvent extends Event {
	private final int originalBatchIndex;
	private final int newBatchIndex;

	public BatchChangeEvent(final Context context, final int originalBatchIndex, final int newBatchIndex) {
		super(context);
		this.originalBatchIndex = originalBatchIndex;
		this.newBatchIndex = newBatchIndex;
	}

	public int getOriginalBatchIndex() {
		return originalBatchIndex;
	}

	public int getNewBatchIndex() {
		return newBatchIndex;
	}
	
	public String toString() {
		return "Batch index was: <" + originalBatchIndex + "> and is now: <" + newBatchIndex + ">" + super.toString();
	}

}
