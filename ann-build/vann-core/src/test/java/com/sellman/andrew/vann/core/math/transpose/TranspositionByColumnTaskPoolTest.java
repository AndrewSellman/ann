package com.sellman.andrew.vann.core.math.transpose;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class TranspositionByColumnTaskPoolTest {

	private TranspositionByColumnTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<TranspositionByColumnTask> objectPool = new GenericObjectPool<>(new TranspositionByColumnTaskFactory());
		taskPool = new TranspositionByColumnTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<TranspositionByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
