package com.sellman.andrew.ann.core.math.add;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class AdditionByColumnTaskPoolTest {

	private AdditionByColumnTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<AdditionByColumnTask> objectPool = new GenericObjectPool<>(new AdditionByColumnTaskFactory());
		taskPool = new AdditionByColumnTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<AdditionByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
