package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class UpdationByRowTaskFactoryTest {

	private UpdationByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new UpdationByRowTaskFactory();
	}

	@Test
	public void create() {
		UpdationByRowTask task1 = factory.create();
		UpdationByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		UpdationByRowTask task = new UpdationByRowTask();

		PooledObject<UpdationByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		UpdationByRowTask task = mock(UpdationByRowTask.class);
		PooledObject<UpdationByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
