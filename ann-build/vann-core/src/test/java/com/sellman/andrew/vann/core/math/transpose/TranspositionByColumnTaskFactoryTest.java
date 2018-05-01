package com.sellman.andrew.vann.core.math.transpose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class TranspositionByColumnTaskFactoryTest {

	private TranspositionByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new TranspositionByColumnTaskFactory();
	}

	@Test
	public void create() {
		TranspositionByColumnTask task1 = factory.create();
		TranspositionByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		TranspositionByColumnTask task = new TranspositionByColumnTask();

		PooledObject<TranspositionByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		TranspositionByColumnTask task = mock(TranspositionByColumnTask.class);
		PooledObject<TranspositionByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
