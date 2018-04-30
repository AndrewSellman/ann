package com.sellman.andrew.ann.core.math.subtract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class SubtractionByColumnTaskFactoryTest {

	private SubtractionByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new SubtractionByColumnTaskFactory();
	}

	@Test
	public void create() {
		SubtractionByColumnTask task1 = factory.create();
		SubtractionByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		SubtractionByColumnTask task = new SubtractionByColumnTask();

		PooledObject<SubtractionByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		SubtractionByColumnTask task = mock(SubtractionByColumnTask.class);
		PooledObject<SubtractionByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
