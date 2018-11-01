package com.sellman.andrew.vann.core.training.evaluator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.training.TrainingProgress;

@RunWith(MockitoJUnitRunner.class)
public class ProportionToLearningRateMomentumEvaluatorTest {
	private static final double INITIAL_MOMENTUM = 0.1;
	private static final double PROPORTION = 0.0095;

	@Mock
	private EventManager eventManager;

	private ProportionToLearningRateMomentumEvaluator evaluator;
	private TrainingProgress trainingProgress;

	@Before
	public void prepareTest() {
		trainingProgress = new TrainingProgress(null, eventManager, 0, 0, 0);
		evaluator = new ProportionToLearningRateMomentumEvaluator(INITIAL_MOMENTUM, PROPORTION);
	}

	@Test
	public void getAdjustedMomentum() {
		trainingProgress.setLearningRate(0.01);
		double newMomentum = evaluator.getAdjustedMomentum(trainingProgress);
		assertEquals(0.95, newMomentum, 0.0);

		trainingProgress.setLearningRate(0.1);
		newMomentum = evaluator.getAdjustedMomentum(trainingProgress);
		assertEquals(0.0949, newMomentum, 0.01);

		trainingProgress.setLearningRate(0.5);
		newMomentum = evaluator.getAdjustedMomentum(trainingProgress);
		assertEquals(0.019, newMomentum, 0.0);

		trainingProgress.setLearningRate(0.75);
		newMomentum = evaluator.getAdjustedMomentum(trainingProgress);
		assertEquals(0.0126, newMomentum, 0.01);

		trainingProgress.setLearningRate(1.0);
		newMomentum = evaluator.getAdjustedMomentum(trainingProgress);
		assertEquals(0.0095, newMomentum, 0.0);

	}

}
