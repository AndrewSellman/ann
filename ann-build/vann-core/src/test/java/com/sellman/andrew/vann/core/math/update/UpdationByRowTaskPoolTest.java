package com.sellman.andrew.vann.core.math.update;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class UpdationByRowTaskPoolTest {

	private UpdationByRowTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<UpdationByRowTask> objectPool = new GenericObjectPool<>(new UpdationByRowTaskFactory());
		taskPool = new UpdationByRowTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<UpdationByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
