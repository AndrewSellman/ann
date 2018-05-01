package com.sellman.andrew.vann.core.math.transpose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class TranspositionByRowTaskFactoryTest {

	private TranspositionByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new TranspositionByRowTaskFactory();
	}

	@Test
	public void create() {
		TranspositionByRowTask task1 = factory.create();
		TranspositionByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		TranspositionByRowTask task = new TranspositionByRowTask();

		PooledObject<TranspositionByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		TranspositionByRowTask task = mock(TranspositionByRowTask.class);
		PooledObject<TranspositionByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
