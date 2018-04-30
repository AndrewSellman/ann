package com.sellman.andrew.vann.core.event;

public class BatchChangeEvent extends Event {
	private final int originalBatchIndex;
	private final int newBatchIndex;

	public BatchChangeEvent(final Context context, final int originalBatchIndex, final int newBatchIndex) {
		super(context);
		this.originalBatchIndex = originalBatchIndex;
		this.newBatchIndex = newBatchIndex;
	}

	public final int getOriginalBatchIndex() {
		return originalBatchIndex;
	}

	public final int getNewBatchIndex() {
		return newBatchIndex;
	}
	
	public final String toString() {
		return "Batch index was: <" + originalBatchIndex + "> and is now: <" + newBatchIndex + ">" + super.toString();
	}

}
