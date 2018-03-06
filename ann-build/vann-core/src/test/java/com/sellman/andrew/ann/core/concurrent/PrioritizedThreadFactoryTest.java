package com.sellman.andrew.ann.core.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadFactory;

import org.junit.Before;
import org.junit.Test;

public class PrioritizedThreadFactoryTest {
	private ThreadFactory factory;
	private Priority priority = Priority.LOW;
	
	@Before
	public void prepareTest() {
		factory = new PrioritizedThreadFactory(priority);
	}
	
	@Test
	public void newThread() {
		Thread thread1 = factory.newThread(null);
		assertEquals(priority.getThreadPriority(), thread1.getPriority());
		assertTrue(thread1.getName().contains(priority.getDescription()));
		assertFalse(thread1.isDaemon());
		
		Thread thread2 = factory.newThread(null);
		assertFalse(thread1.getName().equals(thread2.getName()));
	}
	
}
