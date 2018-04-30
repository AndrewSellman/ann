package com.sellman.andrew.vann.core.concurrent;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.concurrent.AbstractTask;

public class AbstractTaskTest {
		private AbstractTask task;
	
	@Before
	public void prepareTest() {
		task = mock(AbstractTask.class, CALLS_REAL_METHODS);
		doNothing().when(task).execute();
	}
	
	@Test
	public void test() throws Exception {
		task.run();
		verify(task).execute();
	}
}
