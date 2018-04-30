package com.sellman.andrew.vann.core.math.multiply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.commons.pool2.PooledObject;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByColumnTask;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByColumnTaskFactory;

public class HadamardMultiplicationByColumnTaskFactoryTest {

	private HadamardMultiplicationByColumnTaskFactory factory;

	@Before
	public void prepareTest() {
		factory = new HadamardMultiplicationByColumnTaskFactory();
	}

	@Test
	public void create() {
		HadamardMultiplicationByColumnTask task1 = factory.create();
		HadamardMultiplicationByColumnTask task2 = factory.create();
		assertNotSame(task1, task2);
	}

	@Test
	public void wrap() {
		HadamardMultiplicationByColumnTask task = new HadamardMultiplicationByColumnTask();

		PooledObject<HadamardMultiplicationByColumnTask> wrappedTask = factory.wrap(task);
		assertEquals(task, wrappedTask.getObject());
	}

	@Test
	public void passivateObject() {
		HadamardMultiplicationByColumnTask task = mock(HadamardMultiplicationByColumnTask.class);
		PooledObject<HadamardMultiplicationByColumnTask> wrappedTask = factory.wrap(task);

		factory.passivateObject(wrappedTask);
		verify(task).recycle();
	}

}
