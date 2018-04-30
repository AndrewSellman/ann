package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

public class HadamardMultiplicationByRowTaskFactoryTest {

	private HadamardMultiplicationByRowTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new HadamardMultiplicationByRowTaskFactory();
	}

	@Test
	public void create() {
		HadamardMultiplicationByRowTask task1 = factory.create();
		HadamardMultiplicationByRowTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		HadamardMultiplicationByRowTask task = new HadamardMultiplicationByRowTask();

		PooledObject<HadamardMultiplicationByRowTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		HadamardMultiplicationByRowTask task = mock(HadamardMultiplicationByRowTask.class);
		PooledObject<HadamardMultiplicationByRowTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
