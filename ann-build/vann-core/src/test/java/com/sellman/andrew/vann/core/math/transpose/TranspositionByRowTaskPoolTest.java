package com.sellman.andrew.vann.core.math.transpose;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class TranspositionByRowTaskPoolTest {

	private TranspositionByRowTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<TranspositionByRowTask> objectPool = new GenericObjectPool<>(new TranspositionByRowTaskFactory());
		taskPool = new TranspositionByRowTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<TranspositionByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
