package com.sellman.andrew.vann.core.math.multiply;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationByColumnTask;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationByColumnTaskFactory;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationByColumnTaskPool;

public class StandardMultiplicationByColumnTaskPoolTest {

	private StandardMultiplicationByColumnTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<StandardMultiplicationByColumnTask> objectPool = new GenericObjectPool<>(new StandardMultiplicationByColumnTaskFactory());
		taskPool = new StandardMultiplicationByColumnTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<StandardMultiplicationByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
