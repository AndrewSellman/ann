package com.sellman.andrew.ann.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.math.Vector;

public class NormalizerTest {
	private Normalizer normalizer;
	private List<TrainingItem> trainingItems;

	@Before
	public void prepareTest() {
		normalizer = new Normalizer();

		trainingItems = new ArrayList<TrainingItem>();
		Vector input = new Vector(new double[] { 35, 36 });
		trainingItems.add(new TrainingItem(input, new Vector(1)));

		input = new Vector(new double[] { 36, 46 });
		trainingItems.add(new TrainingItem(input, new Vector(1)));

		input = new Vector(new double[] { 46, 68 });
		trainingItems.add(new TrainingItem(input, new Vector(1)));

		input = new Vector(new double[] { 68, 70 });
		trainingItems.add(new TrainingItem(input, new Vector(1)));

		input = new Vector(new double[] { 70, 35 });
		trainingItems.add(new TrainingItem(input, new Vector(1)));
	}

	@Test
	public void normalize() {
		List<TrainingItem> normTrainingItems = normalizer.normalize(trainingItems);
		assertEquals(trainingItems.size(), normTrainingItems.size());

		assertEquals(-0.9412, normTrainingItems.get(0).getInput().getValue(0), .01);
		assertEquals(-0.8824, normTrainingItems.get(1).getInput().getValue(0), .01);
		assertEquals(-0.2941, normTrainingItems.get(2).getInput().getValue(0), .01);
		assertEquals(1.0, normTrainingItems.get(3).getInput().getValue(0), .01);
		assertEquals(1.1176, normTrainingItems.get(4).getInput().getValue(0), .01);

		assertEquals(-0.8824, normTrainingItems.get(0).getInput().getValue(1), .01);
		assertEquals(-0.2941, normTrainingItems.get(1).getInput().getValue(1), .01);
		assertEquals(1.0, normTrainingItems.get(2).getInput().getValue(1), .01);
		assertEquals(1.1176, normTrainingItems.get(3).getInput().getValue(1), .01);
		assertEquals(-0.9412, normTrainingItems.get(4).getInput().getValue(1), .01);

		for (int i = 0; i < trainingItems.size(); i++) {
			assertSame(trainingItems.get(i).getExpectedOutput(), normTrainingItems.get(i).getExpectedOutput());
		}
	}

}
