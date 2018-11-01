package com.sellman.andrew.vann.core.training;

import java.util.List;

public interface TrainingExamplesSplitter {

	public SplitTrainingExamples split(final List<TrainingExample> allTrainingItems, final TrainingProgress trainingProgress);
	
}
