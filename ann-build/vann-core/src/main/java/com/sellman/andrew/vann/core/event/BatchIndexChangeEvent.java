package com.sellman.andrew.vann.core.event;

public class BatchIndexChangeEvent extends IntegerValueEvent {

	public BatchIndexChangeEvent(final Context context, final int batchIndex) {
		super(context, batchIndex);
	}

	public final int getBatchIndex() {
		return getValue();
	}

	public final String toString() {
		return "Batch index is " + getBatchIndex() + " " + super.toString();
	}

}
