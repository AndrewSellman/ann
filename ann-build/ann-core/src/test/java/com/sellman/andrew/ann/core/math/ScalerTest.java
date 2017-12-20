package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

public class ScalerTest {
	private static final double SOURCE_RANGE_MIN = -1000000.0;
	private static final double SOURCE_RANGE_MAX = 1000000.0;
	private static final double TARGET_RANGE_MIN = 0.0;
	private static final double TARGET_RANGE_MAX = 1.0;
	
	private Range scaler;
	
	@Before
	public void prepareTest() {
		scaler = new Range(SOURCE_RANGE_MIN, SOURCE_RANGE_MAX, TARGET_RANGE_MIN, TARGET_RANGE_MAX);
	}

	@Test
	public void scale() {
		for (int i = (int)SOURCE_RANGE_MIN; i < (int)SOURCE_RANGE_MAX; i++) {
			double value = i + ThreadLocalRandom.current().nextDouble(0, 1);
			double down = scaler.scaleDown(value);
			assertTrue(down >= TARGET_RANGE_MIN && down <= TARGET_RANGE_MAX);
			double up = scaler.scaleUp(down);
			assertEquals(value, up, 0.000000001);
		}
	}

}
