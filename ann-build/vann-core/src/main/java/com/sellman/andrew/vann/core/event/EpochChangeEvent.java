package com.sellman.andrew.vann.core.event;

public class EpochChangeEvent extends Event {
	private final int originalEpochIndex;
	private final int newEpochIndex;

	public EpochChangeEvent(final Context context, final int originalEpochIndex, final int newEpochIndex) {
		super(context);
		this.originalEpochIndex = originalEpochIndex;
		this.newEpochIndex = newEpochIndex;
	}

	public final int getOriginalEpochIndex() {
		return originalEpochIndex;
	}

	public final int getNewEpochIndex() {
		return newEpochIndex;
	}
	
	public final String toString() {
		return "Epoch index was: <" + originalEpochIndex + "> and is now: <" + newEpochIndex + ">" + super.toString();
	}

}
