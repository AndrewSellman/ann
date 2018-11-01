package com.sellman.andrew.vann.core.training;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HoldOutTrainingSplitter implements TrainingExamplesSplitter {
	private final double percentUsedForValidation;
	private final boolean shuffleBeforeSplit;
	private final boolean shuffleEachRequest;
	private SplitTrainingExamples splitExamples;

	public HoldOutTrainingSplitter(final double percentUsedForValidation, final boolean shuffleBeforeSplit, final boolean shuffleEachRequest) {
		this.percentUsedForValidation = percentUsedForValidation;
		this.shuffleBeforeSplit = shuffleBeforeSplit;
		this.shuffleEachRequest = shuffleEachRequest;
	}

	public SplitTrainingExamples split(final List<TrainingExample> allTrainingItems, final TrainingProgress trainingProgress) {
		if (splitExamples == null) {
			initSplitter(allTrainingItems);
		}

		if (shuffleEachRequest) {
			Collections.shuffle(splitExamples.getForTraining());
			Collections.shuffle(splitExamples.getForValidation());
		}

		return splitExamples;
	}

	private void initSplitter(List<TrainingExample> allTrainingItems) {
		if (shuffleBeforeSplit) {
			Collections.shuffle(allTrainingItems);
		}

		int splitIndex = (int) (allTrainingItems.size() * percentUsedForValidation);
		List<TrainingExample> trainingData = allTrainingItems.stream().limit(allTrainingItems.size() - splitIndex).collect(Collectors.toList());
		List<TrainingExample> validationData = allTrainingItems.stream().skip(allTrainingItems.size() - splitIndex).collect(Collectors.toList());
		splitExamples = new SplitTrainingExamples(trainingData, validationData);
	}

}
