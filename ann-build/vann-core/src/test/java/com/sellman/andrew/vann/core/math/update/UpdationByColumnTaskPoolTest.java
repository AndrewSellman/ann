package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class UpdationByColumnTaskPoolTest {

	private UpdationByColumnTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<UpdationByColumnTask> objectPool = new GenericObjectPool<>(new UpdationByColumnTaskFactory());
		taskPool = new UpdationByColumnTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<UpdationByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
