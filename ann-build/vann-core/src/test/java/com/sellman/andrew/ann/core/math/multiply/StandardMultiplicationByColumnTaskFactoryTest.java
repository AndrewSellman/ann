package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class StandardMultiplicationByColumnTaskFactoryTest {

	private StandardMultiplicationByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new StandardMultiplicationByColumnTaskFactory();
	}

	@Test
	public void create() {
		StandardMultiplicationByColumnTask task1 = factory.create();
		StandardMultiplicationByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		StandardMultiplicationByColumnTask task = new StandardMultiplicationByColumnTask();

		PooledObject<StandardMultiplicationByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		StandardMultiplicationByColumnTask task = mock(StandardMultiplicationByColumnTask.class);
		PooledObject<StandardMultiplicationByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
