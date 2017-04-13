package com.sellman.andrew.ann.core.concurrent;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PriorityTest {
	
	@Test
	public void lowPriority() {
		assertEquals(Thread.MIN_PRIORITY, Priority.LOW.getThreadPriority());
		assertEquals("low", Priority.LOW.getDescription());
	}
	
	@Test
	public void normalPriority() {
		assertEquals(Thread.NORM_PRIORITY, Priority.NORMAL.getThreadPriority());
		assertEquals("normal", Priority.NORMAL.getDescription());
	}

	@Test
	public void highPriority() {
		assertEquals(Thread.MAX_PRIORITY, Priority.HIGH.getThreadPriority());
		assertEquals("high", Priority.HIGH.getDescription());
	}

}
