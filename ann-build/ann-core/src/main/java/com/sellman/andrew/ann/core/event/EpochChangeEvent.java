package com.sellman.andrew.ann.core.event;

public class EpochChangeEvent extends Event {
	private final int originalEpochIndex;
	private final int newEpochIndex;

	public EpochChangeEvent(final Context context, final int originalEpochIndex, final int newEpochIndex) {
		super(context);
		this.originalEpochIndex = originalEpochIndex;
		this.newEpochIndex = newEpochIndex;
	}

	public int getOriginalEpochIndex() {
		return originalEpochIndex;
	}

	public int getNewEpochIndex() {
		return newEpochIndex;
	}
	
	public String toString() {
		return "Epoch index was: <" + originalEpochIndex + "> and is now: <" + newEpochIndex + ">" + super.toString();
	}

}
