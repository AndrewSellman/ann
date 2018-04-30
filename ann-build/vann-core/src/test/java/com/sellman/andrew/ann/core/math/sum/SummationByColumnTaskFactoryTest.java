package com.sellman.andrew.ann.core.math.sum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class SummationByColumnTaskFactoryTest {

	private SummationByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new SummationByColumnTaskFactory();
	}

	@Test
	public void create() {
		SummationByColumnTask task1 = factory.create();
		SummationByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		SummationByColumnTask task = new SummationByColumnTask();

		PooledObject<SummationByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		SummationByColumnTask task = mock(SummationByColumnTask.class);
		PooledObject<SummationByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
