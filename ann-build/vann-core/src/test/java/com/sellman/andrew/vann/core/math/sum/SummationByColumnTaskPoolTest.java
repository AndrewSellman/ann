package com.sellman.andrew.vann.core.math.sum;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.sum.SummationByColumnTask;
import com.sellman.andrew.vann.core.math.sum.SummationByColumnTaskFactory;
import com.sellman.andrew.vann.core.math.sum.SummationByColumnTaskPool;

public class SummationByColumnTaskPoolTest {

	private SummationByColumnTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<SummationByColumnTask> objectPool = new GenericObjectPool<>(new SummationByColumnTaskFactory());
		taskPool = new SummationByColumnTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<SummationByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
