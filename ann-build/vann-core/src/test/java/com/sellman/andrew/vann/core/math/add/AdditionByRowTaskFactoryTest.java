package com.sellman.andrew.vann.core.math.add;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.add.AdditionByRowTask;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTaskFactory;

public class AdditionByRowTaskFactoryTest {

	private AdditionByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new AdditionByRowTaskFactory();
	}

	@Test
	public void create() {
		AdditionByRowTask task1 = factory.create();
		AdditionByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		AdditionByRowTask task = new AdditionByRowTask();

		PooledObject<AdditionByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		AdditionByRowTask task = mock(AdditionByRowTask.class);
		PooledObject<AdditionByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
