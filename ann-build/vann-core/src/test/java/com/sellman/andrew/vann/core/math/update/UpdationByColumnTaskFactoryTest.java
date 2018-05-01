package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class UpdationByColumnTaskFactoryTest {

	private UpdationByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new UpdationByColumnTaskFactory();
	}

	@Test
	public void create() {
		UpdationByColumnTask task1 = factory.create();
		UpdationByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		UpdationByColumnTask task = new UpdationByColumnTask();

		PooledObject<UpdationByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		UpdationByColumnTask task = mock(UpdationByColumnTask.class);
		PooledObject<UpdationByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
