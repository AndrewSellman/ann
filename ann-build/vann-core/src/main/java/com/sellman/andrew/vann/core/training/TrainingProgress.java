package com.sellman.andrew.vann.core.training;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.vann.core.event.BatchErrorChangeEvent;
import com.sellman.andrew.vann.core.event.BatchIndexChangeEvent;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.EpochIndexChangeEvent;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.LearningRateChangeEvent;
import com.sellman.andrew.vann.core.event.MomentumChangeEvent;
import com.sellman.andrew.vann.core.event.ResetBatchErrorEvent;
import com.sellman.andrew.vann.core.event.ResetEpochErrorEvent;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeEvent;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;

public class TrainingProgress {
	private final Context context;
	private final EventManager eventManager;
	private final int nthEpochSavePoint;
	private int epochIndex;
	private int batchCount;
	private int batchIndex;
	private double epochError;
	private double lastEpochError;
	private double batchError;
	private double validationError;
	private double lastValidationError;
	private final List<TrainingLayerProgress> trainingLayers;
	private double learningRate;
	private double momentum;
	private boolean rollingBackEpoch;

	public TrainingProgress(final Context context, final EventManager eventManager, int layerCount, int nthEpochSavePoint, int savePointDepth) {
		this.context = context;
		this.eventManager = eventManager;
		this.nthEpochSavePoint = nthEpochSavePoint;

		trainingLayers = new ArrayList<TrainingLayerProgress>(layerCount);
		for (int i = 0; i < layerCount; i++) {
			trainingLayers.add(new TrainingLayerProgress(savePointDepth));
		}

		validationError = Double.MAX_VALUE;
	}

	public void setBatchCount(final int batchCount) {
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
		lastValidationError = this.validationError;
		this.validationError = validationError;
		fireValidationErrorChange();
	}

	// public void resetValidationError() {
	// double originalValidationError = validationError;
	// validationError = 0;
	// // fireResetEpochError(originalEpochError);
	// }

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

	private void fireLearningRateChangeEvent() {
		if (!isAnyListener(LearningRateChangeEvent.class)) {
			return;
		}

		Event event = new LearningRateChangeEvent(context, learningRate);
		fire(event);
	}

	private void fireMomentumChangeEvent() {
		if (!isAnyListener(MomentumChangeEvent.class)) {
			return;
		}

		Event event = new MomentumChangeEvent(context, momentum);
		fire(event);
	}

	private void fire(Event event) {
		eventManager.fire(event);
	}

	private boolean isAnyListener(Class<? extends Event> eventType) {
		return eventManager.isAnyRegisteredListenerFor(eventType);
	}

	public double getLastValidationError() {
		return lastValidationError;
	}

	public double getValidationError() {
		return validationError;
	}

	protected void setInput(InspectableMatrix input, int layerIndex) {
		trainingLayers.get(layerIndex).setInput(input);
	}

	protected InspectableMatrix getInput(int layerIndex) {
		return trainingLayers.get(layerIndex).getInput();
	}

	protected void setOutput(InspectableMatrix output, int layerIndex) {
		trainingLayers.get(layerIndex).setOutput(output);
	}

	protected InspectableMatrix getOutput(int layerIndex) {
		return trainingLayers.get(layerIndex).getOutput();
	}

	protected void setBiasedWeightedInput(InspectableMatrix biasedWeightedInput, int layerIndex) {
		trainingLayers.get(layerIndex).setBiasedWeightedInput(biasedWeightedInput);
	}

	protected InspectableMatrix getBiasedWeightedInput(int layerIndex) {
		return trainingLayers.get(layerIndex).getBiasedWeightedInput();
	}

	protected void setBiasedWeightedPrimeOutput(InspectableMatrix biasedWeightedPrimeOutput, int layerIndex) {
		trainingLayers.get(layerIndex).setBiasedWeightedPrimeOutput(biasedWeightedPrimeOutput);
	}

	protected InspectableMatrix getBiasedWeightedPrimeOutput(int layerIndex) {
		return trainingLayers.get(layerIndex).getBiasedWeightedPrimeOutput();
	}

	protected void setOutputDelta(int layerIndex, InspectableMatrix outputDelta) {
		trainingLayers.get(layerIndex).setOutputDelta(outputDelta);
	}

	protected InspectableMatrix getOutputDelta(int layerIndex) {
		return trainingLayers.get(layerIndex).getOutputDelta();
	}

	public void setLastWeightDelta(int layerIndex, InspectableMatrix weightDelta) {
		trainingLayers.get(layerIndex).setLastWeightDelta(weightDelta);
	}

	public InspectableMatrix getLastWeightDelta(int layerIndex) {
		return trainingLayers.get(layerIndex).getLastWeightDelta();
	}

	public void setLastBiasDelta(int layerIndex, RowVector biasDelta) {
		trainingLayers.get(layerIndex).setLastBiasDelta(biasDelta);
	}

	public RowVector getLastBiasDelta(int layerIndex) {
		return trainingLayers.get(layerIndex).getLastBiasDelta();
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		if (this.learningRate == learningRate) {
			return;
		}

		this.learningRate = learningRate;
		fireLearningRateChangeEvent();
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		if (this.momentum == momentum) {
			return;
		}

		this.momentum = momentum;
		fireMomentumChangeEvent();
	}

	public boolean isEpochSavePoint() {
		if (nthEpochSavePoint == Integer.MAX_VALUE) {
			return false;
		}

		return epochIndex / nthEpochSavePoint == 1.0 * epochIndex / nthEpochSavePoint;
	}

	public void setWeightsSavePoint(int layerIndex, Matrix weights) {
		trainingLayers.get(layerIndex).setWeightsSavePoint(weights);
	}

	public Matrix getWeightsSavePoint(int layerIndex) {
		return trainingLayers.get(layerIndex).getWeightsSavePoint();
	}

	public void setBiasSavePoint(int layerIndex, RowVector bias) {
		trainingLayers.get(layerIndex).setBiasSavePoint(bias);
	}

	public RowVector getBiasSavePoint(int layerIndex) {
		return trainingLayers.get(layerIndex).getBiasSavePoint();
	}

	public boolean isRollingBackEpoch() {
		return rollingBackEpoch;
	}

	public void setRollingBackEpoch(boolean rollingBackEpoch) {
		this.rollingBackEpoch = rollingBackEpoch;
	}

}
