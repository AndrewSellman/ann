package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class HadamardMultiplicationByColumnTaskPoolTest {

	private HadamardMultiplicationByColumnTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<HadamardMultiplicationByColumnTask> objectPool = new GenericObjectPool<>(new HadamardMultiplicationByColumnTaskFactory());
		taskPool = new HadamardMultiplicationByColumnTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<HadamardMultiplicationByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
