package com.sellman.andrew.vann.core.training;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.RowVector;

public class TrainingBatchFactory {
	private final InspectableMatrixFactory matrixFactory;
	
	public TrainingBatchFactory(InspectableMatrixFactory matrixFactory) {
		this.matrixFactory = matrixFactory;
	}

	public TrainingBatch createFor(List<TrainingExample> trainingItems) {
		List<RowVector> inputs = getInputs(trainingItems);
		InspectableMatrix input = matrixFactory.createFor(inputs);

		List<RowVector> expectedOutputs = getExpectedOutputs(trainingItems);
		InspectableMatrix expectedOutput = matrixFactory.createFor(expectedOutputs);
		
		return new TrainingBatch(input, expectedOutput);
	}

	private List<RowVector> getExpectedOutputs(List<TrainingExample> trainingItems) {
		List<RowVector> expectedOutputs = new ArrayList<>(trainingItems.size());
		for (TrainingExample trainingItem : trainingItems) {
			expectedOutputs.add(trainingItem.getExpectedOutput());
		}

		return expectedOutputs;
	}

	private List<RowVector> getInputs(List<TrainingExample> trainingItems) {
		List<RowVector> inputs = new ArrayList<>(trainingItems.size());
		for (TrainingExample trainingItem : trainingItems) {
			inputs.add(trainingItem.getNormalizedInput());
		}
		
		return inputs;
	}

}
