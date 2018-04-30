package com.sellman.andrew.vann.core.math.scale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.scale.ScalerByColumnTask;
import com.sellman.andrew.vann.core.math.scale.ScalerByColumnTaskFactory;

public class ScalerByColumnTaskFactoryTest {

	private ScalerByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new ScalerByColumnTaskFactory();
	}

	@Test
	public void create() {
		ScalerByColumnTask task1 = factory.create();
		ScalerByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		ScalerByColumnTask task = new ScalerByColumnTask();

		PooledObject<ScalerByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		ScalerByColumnTask task = mock(ScalerByColumnTask.class);
		PooledObject<ScalerByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
