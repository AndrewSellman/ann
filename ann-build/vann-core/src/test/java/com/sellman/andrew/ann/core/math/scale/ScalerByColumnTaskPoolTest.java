package com.sellman.andrew.ann.core.math.scale;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class ScalerByColumnTaskPoolTest {

	private ScalerByColumnTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<ScalerByColumnTask> objectPool = new GenericObjectPool<>(new ScalerByColumnTaskFactory());
		taskPool = new ScalerByColumnTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<ScalerByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
