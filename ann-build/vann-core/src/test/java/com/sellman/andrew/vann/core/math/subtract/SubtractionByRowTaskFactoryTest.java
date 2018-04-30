package com.sellman.andrew.vann.core.math.subtract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTask;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTaskFactory;

public class SubtractionByRowTaskFactoryTest {

	private SubtractionByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new SubtractionByRowTaskFactory();
	}

	@Test
	public void create() {
		SubtractionByRowTask task1 = factory.create();
		SubtractionByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		SubtractionByRowTask task = new SubtractionByRowTask();

		PooledObject<SubtractionByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		SubtractionByRowTask task = mock(SubtractionByRowTask.class);
		PooledObject<SubtractionByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
