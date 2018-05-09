package com.sellman.andrew.vann.core.training;

import com.sellman.andrew.vann.core.event.BatchIndexChangeEvent;
import com.sellman.andrew.vann.core.event.BatchErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochIndexChangeEvent;
import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.ResetBatchErrorEvent;
import com.sellman.andrew.vann.core.event.ResetEpochErrorEvent;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeEvent;

public class TrainingProgress {
	private final Context context;
	private final EventManager eventManager;
	private final int batchCount;
	private int epochIndex;
	private int batchIndex;
	private double epochError;
	private double lastEpochError;
	private double batchError;
	private double validationError;
	
	public TrainingProgress(final Context context, final EventManager eventManager, int batchCount) {
		this.context = context;
		this.eventManager = eventManager;
		this.batchCount = batchCount;
	}

	public int getBatchCount() {
		return batchCount;
	}

	public int getEpochIndex() {
		return epochIndex;
	}

	public void incrementEpochIndex() {
		epochIndex++;
		fireEpochChange();
	}

	public void resetEpochIndex() {
		epochIndex = 0;
		fireEpochChange();
	}

	public int getBatchIndex() {
		return batchIndex;
	}

	public void incrementBatchIndex() {
		batchIndex++;
		fireBatchChange();
	}

	public void resetBatchIndex() {
		batchIndex = 0;
		fireBatchChange();
	}

	public double getEpochError() {
		return epochError;
	}

	public void setEpochError(double epochError) {
		lastEpochError = this.epochError;
		this.epochError = epochError;
		fireEpochErrorChange();
	}

	public double getLastEpochError() {
		return lastEpochError;
	}

	public void resetEpochError() {
		epochError = 0;
		fireResetEpochError();
	}

	public double getBatchError() {
		return batchError;
	}

	public void resetBatchError() {
		this.batchError = 0;
		fireResetBatchError();
	}

	public void accumulateBatchError(double batchError) {
		this.batchError += batchError;
		fireBatchErrorChange();
	}

	public void setValidationError(double validationError) {
		this.validationError = validationError;
		fireValidationErrorChange();
	}

	public void resetValidationError() {
		double originalValidationError = validationError;
		validationError = 0;
//		fireResetEpochError(originalEpochError);
	}

	private void fireEpochChange() {
		if (!isAnyListener(EpochIndexChangeEvent.class)) {
			return;
		}

		Event event = new EpochIndexChangeEvent(context, epochIndex);
		fire(event);
	}

	private void fireResetEpochError() {
		if (!isAnyListener(ResetEpochErrorEvent.class)) {
			return;
		}

		Event event = new ResetEpochErrorEvent(context);
		fire(event);
	}

	private void fireResetBatchError() {
		if (!isAnyListener(ResetBatchErrorEvent.class)) {
			return;
		}

		Event event = new ResetBatchErrorEvent(context);
		fire(event);
	}

	private void fireValidationErrorChange() {
		if (!isAnyListener(ValidationErrorChangeEvent.class)) {
			return;
		}

		Event event = new ValidationErrorChangeEvent(context, validationError);
		fire(event);
	}

	private void fireEpochErrorChange() {
		if (!isAnyListener(EpochErrorChangeEvent.class)) {
			return;
		}

		Event event = new EpochErrorChangeEvent(context, epochError);
		fire(event);
	}

	private void fireBatchErrorChange() {
		if (!isAnyListener(BatchErrorChangeEvent.class)) {
			return;
		}

		Event event = new BatchErrorChangeEvent(context, batchError);
		fire(event);
	}

	private void fireBatchChange() {
		if (!isAnyListener(BatchIndexChangeEvent.class)) {
			return;
		}

		Event event = new BatchIndexChangeEvent(context, batchIndex);
		fire(event);
	}

	private void fire(Event event) {
		eventManager.fire(event);
	}

	private boolean isAnyListener(Class<? extends Event> eventType) {
		return eventManager.isAnyRegisteredListenerFor(eventType);
	}

	public double getValidationError() {
		return validationError;
	}

}
