package com.sellman.andrew.vann.core.training;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.vann.core.math.RowVector;

public class Normalizer {

	public List<TrainingExample> normalize(final List<TrainingExample> trainingItems) {
		int countOfColumnsToBeNormalized = trainingItems.get(0).getNormalizedInput().getColumnCount();
		List<TrainingExample> normedTrainingItems = getInitialNormedTrainingItems(trainingItems, countOfColumnsToBeNormalized);

		for (int c = 0; c < countOfColumnsToBeNormalized; c++) {
			normalize(trainingItems, c, normedTrainingItems);
		}

		return normedTrainingItems;
	}

	private void normalize(final List<TrainingExample> trainingItems, final int columnToNormalize, final List<TrainingExample> normedTrainingItems) {
		double mean = getMean(trainingItems, columnToNormalize);
		double standardDeviation = getStandardDeviation(trainingItems, columnToNormalize, mean);
		normalize(trainingItems, columnToNormalize, normedTrainingItems, mean, standardDeviation);
	}

	private void normalize(final List<TrainingExample> trainingItems, final int columnToNormalize, final List<TrainingExample> normedTrainingItems, final double mean, final double standardDeviation) {
		for (int i = 0; i < trainingItems.size(); i++) {
			double normalized = (trainingItems.get(i).getNormalizedInput().getValue(columnToNormalize) - mean) / standardDeviation;
			normedTrainingItems.get(i).getNormalizedInput().setValue(columnToNormalize, normalized);
		}
	}

	private double getStandardDeviation(final List<TrainingExample> trainingItems, final int columnToNormalize, final double mean) {
		double squaredTotal = 0;

		for (TrainingExample item : trainingItems) {
			squaredTotal += Math.pow(item.getNormalizedInput().getValue(columnToNormalize) - mean, 2);
		}

		return Math.sqrt(squaredTotal / (trainingItems.size() - 1));
	}

	private double getMean(final List<TrainingExample> trainingItems, final int columnToNormalize) {
		double total = 0;
		for (TrainingExample item : trainingItems) {
			total += item.getNormalizedInput().getValue(columnToNormalize);
		}

		return total / trainingItems.size();
	}

	private List<TrainingExample> getInitialNormedTrainingItems(final List<TrainingExample> trainingItems, final int inputColumnCount) {
		List<TrainingExample> normTrainingItems = new ArrayList<TrainingExample>(trainingItems.size());
		for (TrainingExample item : trainingItems) {
			normTrainingItems.add(new TrainingExample(null, null, new RowVector(inputColumnCount), item.getExpectedOutput()));
		}

		return normTrainingItems;
	}

}
