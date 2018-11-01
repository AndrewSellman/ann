package com.sellman.andrew.vann.core.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KFoldTrainingSplitter implements TrainingExamplesSplitter {
	private final int foldCount;
	private final List<Fold> folds;
	private final boolean shuffleBeforeSplit;
	private final boolean shuffleEachRequest;
	private int k;

	public KFoldTrainingSplitter(final int foldCount, final boolean shuffleBeforeSplit, final boolean shuffleEachRequest) {
		this.foldCount = foldCount;
		this.shuffleBeforeSplit = shuffleBeforeSplit;
		this.shuffleEachRequest = shuffleEachRequest;
		folds = new ArrayList<>(foldCount);
	}

	public SplitTrainingExamples split(final List<TrainingExample> allTrainingExamples, final TrainingProgress trainingProgress) {
		if (folds.isEmpty()) {
			initFolds(allTrainingExamples);
		}

		if (k == foldCount) {
			k = 0;
			
			if (shuffleBeforeSplit) {
				Collections.shuffle(allTrainingExamples);
			}
		}

		List<TrainingExample> trainingData = new ArrayList<>();
		List<TrainingExample> validationData = new ArrayList<>();
		for (int i = 0; i < folds.size(); i++) {
			int startingIndex = folds.get(i).startingIndex;
			int endingIndex = folds.get(i).endingIndex;

			if (i == k) {
				validationData.addAll(allTrainingExamples.subList(startingIndex, endingIndex));
			} else {
				trainingData.addAll(allTrainingExamples.subList(startingIndex, endingIndex));
			}

		}

		if (shuffleEachRequest) {
			Collections.shuffle(validationData);
			Collections.shuffle(trainingData);
		}

		k++;
		return new SplitTrainingExamples(trainingData, validationData);
	}

	private void initFolds(List<TrainingExample> allTrainingItems) {
		int itemsPerFold = allTrainingItems.size() / foldCount;
		if (itemsPerFold * foldCount < allTrainingItems.size()) {
			itemsPerFold++;
		}

		for (int i = 0; i < foldCount; i++) {
			int startingIndex = i * itemsPerFold;
			int endingIndex = i * itemsPerFold + itemsPerFold;
			if (endingIndex > allTrainingItems.size()) {
				endingIndex = allTrainingItems.size();
			}

			folds.add(new Fold(startingIndex, endingIndex));
		}
	}

	private class Fold {
		int startingIndex;
		int endingIndex;

		Fold(int startingIndex, int endingIndex) {
			this.startingIndex = startingIndex;
			this.endingIndex = endingIndex;
		}

	}

}
