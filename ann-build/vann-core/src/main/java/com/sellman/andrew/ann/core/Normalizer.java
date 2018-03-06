package com.sellman.andrew.ann.core;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.math.Vector;

public class Normalizer {

	public List<TrainingItem> normalize(final List<TrainingItem> trainingItems) {
		int countOfColumnsToBeNormalized = trainingItems.get(0).getInput().getRowCount();
		List<TrainingItem> normedTrainingItems = getInitialNormedTrainingItems(trainingItems, countOfColumnsToBeNormalized);

		for (int c = 0; c < countOfColumnsToBeNormalized; c++) {
			normalize(trainingItems, c, normedTrainingItems);
		}

		return normedTrainingItems;
	}

	private void normalize(final List<TrainingItem> trainingItems, final int columnToNormalize, final List<TrainingItem> normedTrainingItems) {
		double mean = getMean(trainingItems, columnToNormalize);
		double standardDeviation = getStandardDeviation(trainingItems, columnToNormalize, mean);
		normalize(trainingItems, columnToNormalize, normedTrainingItems, mean, standardDeviation);
	}

	private void normalize(final List<TrainingItem> trainingItems, final int columnToNormalize, final List<TrainingItem> normedTrainingItems, final double mean, final double standardDeviation) {
		for (int i = 0; i < trainingItems.size(); i++) {
			double normalized = (trainingItems.get(i).getInput().getValue(columnToNormalize) - mean) / standardDeviation;
			normedTrainingItems.get(i).getInput().setValue(columnToNormalize, normalized);
		}
	}

	private double getStandardDeviation(final List<TrainingItem> trainingItems, final int columnToNormalize, final double mean) {
		double squaredTotal = 0;

		for (TrainingItem item : trainingItems) {
			squaredTotal += Math.pow(item.getInput().getValue(columnToNormalize) - mean, 2);
		}

		return Math.sqrt(squaredTotal / (trainingItems.size() - 1));
	}

	private double getMean(final List<TrainingItem> trainingItems, final int columnToNormalize) {
		double total = 0;
		for (TrainingItem item : trainingItems) {
			total += item.getInput().getValue(columnToNormalize);
		}

		return total / trainingItems.size();
	}

	private List<TrainingItem> getInitialNormedTrainingItems(final List<TrainingItem> trainingItems, final int inputColumnCount) {
		List<TrainingItem> normTrainingItems = new ArrayList<TrainingItem>(trainingItems.size());
		for (TrainingItem item : trainingItems) {
			normTrainingItems.add(new TrainingItem(new Vector(inputColumnCount), item.getExpectedOutput()));
		}

		return normTrainingItems;
	}

}
