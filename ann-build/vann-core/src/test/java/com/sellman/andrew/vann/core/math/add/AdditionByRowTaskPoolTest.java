package com.sellman.andrew.vann.core.math.add;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.add.AdditionByRowTask;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTaskFactory;
import com.sellman.andrew.vann.core.math.add.AdditionByRowTaskPool;

public class AdditionByRowTaskPoolTest {

	private AdditionByRowTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<AdditionByRowTask> objectPool = new GenericObjectPool<>(new AdditionByRowTaskFactory());
		taskPool = new AdditionByRowTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<AdditionByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
