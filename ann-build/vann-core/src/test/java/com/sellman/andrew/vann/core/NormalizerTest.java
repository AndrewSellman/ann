package com.sellman.andrew.vann.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.training.Normalizer;
import com.sellman.andrew.vann.core.training.TrainingExample;

public class NormalizerTest {
	private Normalizer normalizer;
	private List<TrainingExample> trainingItems;

	@Before
	public void prepareTest() {
		normalizer = new Normalizer();

		trainingItems = new ArrayList<TrainingExample>();
		RowVector input = new RowVector(new double[] { 35, 36 });
		trainingItems.add(new TrainingExample(null, null, input, new RowVector(1)));

		input = new RowVector(new double[] { 36, 46 });
		trainingItems.add(new TrainingExample(null, null, input, new RowVector(1)));

		input = new RowVector(new double[] { 46, 68 });
		trainingItems.add(new TrainingExample(null, null, input, new RowVector(1)));

		input = new RowVector(new double[] { 68, 70 });
		trainingItems.add(new TrainingExample(null, null, input, new RowVector(1)));

		input = new RowVector(new double[] { 70, 35 });
		trainingItems.add(new TrainingExample(null, null, input, new RowVector(1)));
	}

	@Test
	public void normalize() {
		List<TrainingExample> normTrainingItems = normalizer.normalize(trainingItems);
		assertEquals(trainingItems.size(), normTrainingItems.size());

		assertEquals(-0.9412, normTrainingItems.get(0).getNormalizedInput().getValue(0), .01);
		assertEquals(-0.8824, normTrainingItems.get(1).getNormalizedInput().getValue(0), .01);
		assertEquals(-0.2941, normTrainingItems.get(2).getNormalizedInput().getValue(0), .01);
		assertEquals(1.0, normTrainingItems.get(3).getNormalizedInput().getValue(0), .01);
		assertEquals(1.1176, normTrainingItems.get(4).getNormalizedInput().getValue(0), .01);

		assertEquals(-0.8824, normTrainingItems.get(0).getNormalizedInput().getValue(1), .01);
		assertEquals(-0.2941, normTrainingItems.get(1).getNormalizedInput().getValue(1), .01);
		assertEquals(1.0, normTrainingItems.get(2).getNormalizedInput().getValue(1), .01);
		assertEquals(1.1176, normTrainingItems.get(3).getNormalizedInput().getValue(1), .01);
		assertEquals(-0.9412, normTrainingItems.get(4).getNormalizedInput().getValue(1), .01);

		for (int i = 0; i < trainingItems.size(); i++) {
			assertSame(trainingItems.get(i).getExpectedOutput(), normTrainingItems.get(i).getExpectedOutput());
		}
	}

}
