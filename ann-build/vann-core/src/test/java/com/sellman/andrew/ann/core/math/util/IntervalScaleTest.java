package com.sellman.andrew.ann.core.math.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class IntervalScaleTest {
	private final double FROM_MIN = 0.0;
	private final double FROM_MAX = 1000.0;
	private final double TO_MIN = -1.0;
	private final double TO_MAX = 1.0;
	
	private IntervalScale scale;
	
	@Before
	public void prepareTest() {
		scale = new IntervalScale(FROM_MIN, FROM_MAX, TO_MIN, TO_MAX);
	}
	
	@Test
	public void scaleUp() {
		Map<Double, Double> scaledDown = new HashMap<>();
		for (double d = FROM_MIN; d <= FROM_MAX; d += 0.001) {
			scaledDown.put(d, scale.down(d));
		}
		
		for (Map.Entry<Double, Double> entry : scaledDown.entrySet()) {
			double scaledUp = scale.up(entry.getValue());
			assertEquals(entry.getKey(), scaledUp, 0.0000000001);
			assertTrue(scaledUp >= FROM_MIN);
			assertTrue(scaledUp <= FROM_MAX);
		}
	}
	
	@Test
	public void scaleDown() {
		Map<Double, Double> scaledUp = new HashMap<>();
		for (double d = TO_MIN; d <= TO_MAX; d += 0.001) {
			scaledUp.put(d, scale.up(d));
		}
		
		for (Map.Entry<Double, Double> entry : scaledUp.entrySet()) {
			double scaledDown = scale.down(entry.getValue());
			assertEquals(entry.getKey(), scaledDown, 0.0000000001);
			assertTrue(scaledDown >= TO_MIN);
			assertTrue(scaledDown <= TO_MAX);
		}
	}

	
}
