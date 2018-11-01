package com.sellman.andrew.vann.core.training;

import java.util.ArrayDeque;
import java.util.Deque;

import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;

public class TrainingLayerProgress {
	private final int savePointDepth;
	private InspectableMatrix input;
	private InspectableMatrix biasedWeightedInput;
	private InspectableMatrix biasedWeightedPrimeOutput;
	private InspectableMatrix output;
	private InspectableMatrix outputDelta;
	private InspectableMatrix lastWeightDelta;
	private RowVector lastBiasDelta;
	private Deque<Matrix> weightsSavePoints;
	private Deque<RowVector> biasSavePoints;

	public TrainingLayerProgress(int savePointDepth) {
		this.savePointDepth = savePointDepth;
		if (savePointDepth > 0) {
			weightsSavePoints = new ArrayDeque<>(savePointDepth);
			biasSavePoints = new ArrayDeque<>(savePointDepth);
		}
	}

	public void setTraining(boolean isTraining) {
		if (!isTraining) {
			setInput(null);
			setBiasedWeightedInput(null);
			setBiasedWeightedPrimeOutput(null);
			setOutput(null);
		}
	}

	public InspectableMatrix getInput() {
		return input;
	}

	public void setInput(InspectableMatrix input) {
		this.input = input;
	}

	public InspectableMatrix getBiasedWeightedInput() {
		return biasedWeightedInput;
	}

	public void setBiasedWeightedInput(InspectableMatrix biasedWeightedInput) {
		this.biasedWeightedInput = biasedWeightedInput;
	}

	public InspectableMatrix getBiasedWeightedPrimeOutput() {
		return biasedWeightedPrimeOutput;
	}

	public void setBiasedWeightedPrimeOutput(InspectableMatrix biasedWeightedPrimeOutput) {
		this.biasedWeightedPrimeOutput = biasedWeightedPrimeOutput;
	}

	public InspectableMatrix getOutput() {
		return output;
	}

	public void setOutput(InspectableMatrix output) {
		this.output = output;
	}

	public InspectableMatrix getOutputDelta() {
		return outputDelta;
	}

	public void setOutputDelta(InspectableMatrix outputDelta) {
		this.outputDelta = outputDelta;
	}

	public void setLastWeightDelta(InspectableMatrix weightDelta) {
		this.lastWeightDelta = weightDelta;
	}

	public InspectableMatrix getLastWeightDelta() {
		return lastWeightDelta;
	}

	public RowVector getLastBiasDelta() {
		return lastBiasDelta;
	}

	public void setLastBiasDelta(RowVector lastBiasDelta) {
		this.lastBiasDelta = lastBiasDelta;
	}

	public Matrix getWeightsSavePoint() {
		Matrix savePoint = weightsSavePoints.pop();
		if (weightsSavePoints.isEmpty()) {
			setWeightsSavePoint(savePoint);
		}

		return savePoint;
	}

	public void setWeightsSavePoint(Matrix weightsSavePoint) {
		if (weightsSavePoints.size() == savePointDepth) {
			weightsSavePoints.removeLast();
		}

		this.weightsSavePoints.push(weightsSavePoint);
	}

	public RowVector getBiasSavePoint() {
		RowVector savePoint = biasSavePoints.pop();
		if (biasSavePoints.isEmpty()) {
			setBiasSavePoint(savePoint);
		}

		return savePoint;
	}

	public void setBiasSavePoint(RowVector biasSavePoint) {
		if (biasSavePoints.size() == savePointDepth) {
			biasSavePoints.removeLast();
		}

		this.biasSavePoints.push(biasSavePoint);
	}

}
