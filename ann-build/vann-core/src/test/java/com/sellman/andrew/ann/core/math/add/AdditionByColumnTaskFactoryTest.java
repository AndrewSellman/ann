package com.sellman.andrew.ann.core.math.add;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class AdditionByColumnTaskFactoryTest {

	private AdditionByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new AdditionByColumnTaskFactory();
	}

	@Test
	public void create() {
		AdditionByColumnTask task1 = factory.create();
		AdditionByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		AdditionByColumnTask task = new AdditionByColumnTask();

		PooledObject<AdditionByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		AdditionByColumnTask task = mock(AdditionByColumnTask.class);
		PooledObject<AdditionByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
