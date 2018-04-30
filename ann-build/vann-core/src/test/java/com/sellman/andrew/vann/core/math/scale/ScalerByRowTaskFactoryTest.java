package com.sellman.andrew.vann.core.math.scale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.scale.ScalerByRowTask;
import com.sellman.andrew.vann.core.math.scale.ScalerByRowTaskFactory;

public class ScalerByRowTaskFactoryTest {

	private ScalerByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new ScalerByRowTaskFactory();
	}

	@Test
	public void create() {
		ScalerByRowTask task1 = factory.create();
		ScalerByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		ScalerByRowTask task = new ScalerByRowTask();

		PooledObject<ScalerByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		ScalerByRowTask task = mock(ScalerByRowTask.class);
		PooledObject<ScalerByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
