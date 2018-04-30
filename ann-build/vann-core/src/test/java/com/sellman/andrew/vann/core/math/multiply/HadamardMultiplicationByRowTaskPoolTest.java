package com.sellman.andrew.vann.core.math.multiply;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByRowTask;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByRowTaskFactory;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationByRowTaskPool;

public class HadamardMultiplicationByRowTaskPoolTest {

	private HadamardMultiplicationByRowTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<HadamardMultiplicationByRowTask> objectPool = new GenericObjectPool<>(new HadamardMultiplicationByRowTaskFactory());
		taskPool = new HadamardMultiplicationByRowTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<HadamardMultiplicationByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
