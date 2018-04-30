package com.sellman.andrew.vann.core.math.sum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.sum.SummationByRowTask;
import com.sellman.andrew.vann.core.math.sum.SummationByRowTaskFactory;

public class SummationByRowTaskFactoryTest {

	private SummationByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new SummationByRowTaskFactory();
	}

	@Test
	public void create() {
		SummationByRowTask task1 = factory.create();
		SummationByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		SummationByRowTask task = new SummationByRowTask();

		PooledObject<SummationByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		SummationByRowTask task = mock(SummationByRowTask.class);
		PooledObject<SummationByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
