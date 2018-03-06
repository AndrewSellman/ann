package com.sellman.andrew.ann.core.training;

import com.sellman.andrew.ann.core.event.BatchChangeEvent;
import com.sellman.andrew.ann.core.event.BatchErrorChangeEvent;
import com.sellman.andrew.ann.core.event.Context;
import com.sellman.andrew.ann.core.event.EpochChangeEvent;
import com.sellman.andrew.ann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.ann.core.event.Event;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.event.ResetBatchErrorEvent;
import com.sellman.andrew.ann.core.event.ResetEpochErrorEvent;
import com.sellman.andrew.ann.core.event.ValidationErrorChangeEvent;

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
		int originalEpochIndex = epochIndex;
		epochIndex++;
		fireEpochChange(originalEpochIndex);
	}

	public void resetEpochIndex() {
		int originalEpochIndex = epochIndex;
		epochIndex = 0;
		fireEpochChange(originalEpochIndex);
	}

	public int getBatchIndex() {
		return batchIndex;
	}

	public void incrementBatchIndex() {
		int originalBatchIndex = batchIndex;
		batchIndex++;
		fireBatchChange(originalBatchIndex);
	}

	public void resetBatchIndex() {
		int originalBatchIndex = batchIndex;
		batchIndex = 0;
		fireBatchChange(originalBatchIndex);
	}

	public double getEpochError() {
		return epochError;
	}

	public void setEpochError(double epochError) {
		double originalEpochError = this.epochError;
		this.epochError = epochError;
		fireEpochErrorChange(originalEpochError);
		lastEpochError = originalEpochError;
	}

	public double getLastEpochError() {
		return lastEpochError;
	}

	public void resetEpochError() {
		double originalEpochError = epochError;
		epochError = 0;
		fireResetEpochError(originalEpochError);
	}

	public double getBatchError() {
		return batchError;
	}

	public void resetBatchError() {
		double originalBatchError = batchError;
		this.batchError = 0;
		fireResetBatchError(originalBatchError);
	}

	public void accumulateBatchError(double batchError) {
		double originalBatchError = batchError;
		this.batchError += batchError;
		fireBatchErrorChange(originalBatchError);
	}

	public void setValidationError(double validationError) {
		double originalValidationError = this.validationError;
		this.validationError = validationError;
		fireValidationErrorChange(originalValidationError);
	}

	public void resetValidationError() {
		double originalValidationError = validationError;
		validationError = 0;
//		fireResetEpochError(originalEpochError);
	}

	private void fireEpochChange(int originalEpochIndex) {
		if (!isAnyListener(EpochChangeEvent.class)) {
			return;
		}

		Event event = new EpochChangeEvent(context, originalEpochIndex, epochIndex);
		fire(event);
	}

	private void fireResetEpochError(double originalEpochError) {
		if (!isAnyListener(ResetEpochErrorEvent.class)) {
			return;
		}

		Event event = new ResetEpochErrorEvent(context, originalEpochError);
		fire(event);
	}

	private void fireResetBatchError(double originalBatchError) {
		if (!isAnyListener(ResetBatchErrorEvent.class)) {
			return;
		}

		Event event = new ResetBatchErrorEvent(context, originalBatchError);
		fire(event);
	}

	private void fireValidationErrorChange(double originalValidationError) {
		if (!isAnyListener(ValidationErrorChangeEvent.class)) {
			return;
		}

		Event event = new ValidationErrorChangeEvent(context, originalValidationError, validationError);
		fire(event);
	}

	private void fireEpochErrorChange(double originalEpochError) {
		if (!isAnyListener(EpochErrorChangeEvent.class)) {
			return;
		}

		Event event = new EpochErrorChangeEvent(context, originalEpochError, epochError);
		fire(event);
	}

	private void fireBatchErrorChange(double originalBatchError) {
		if (!isAnyListener(BatchErrorChangeEvent.class)) {
			return;
		}

		Event event = new BatchErrorChangeEvent(context, originalBatchError, batchError);
		fire(event);
	}

	private void fireBatchChange(int originalBatchIndex) {
		if (!isAnyListener(BatchChangeEvent.class)) {
			return;
		}

		Event event = new BatchChangeEvent(context, originalBatchIndex, batchIndex);
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
