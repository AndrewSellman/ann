package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class StandardMultiplicationByRowTaskFactoryTest {

	private StandardMultiplicationByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new StandardMultiplicationByRowTaskFactory();
	}

	@Test
	public void create() {
		StandardMultiplicationByRowTask task1 = factory.create();
		StandardMultiplicationByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		StandardMultiplicationByRowTask task = new StandardMultiplicationByRowTask();

		PooledObject<StandardMultiplicationByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		StandardMultiplicationByRowTask task = mock(StandardMultiplicationByRowTask.class);
		PooledObject<StandardMultiplicationByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
