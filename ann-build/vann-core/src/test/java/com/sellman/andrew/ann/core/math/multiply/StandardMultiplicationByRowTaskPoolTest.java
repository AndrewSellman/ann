package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class StandardMultiplicationByRowTaskPoolTest {

	private StandardMultiplicationByRowTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<StandardMultiplicationByRowTask> objectPool = new GenericObjectPool<>(new StandardMultiplicationByRowTaskFactory());
		taskPool = new StandardMultiplicationByRowTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<StandardMultiplicationByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
