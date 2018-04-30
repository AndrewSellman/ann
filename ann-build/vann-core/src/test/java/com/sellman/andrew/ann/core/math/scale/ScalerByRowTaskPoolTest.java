package com.sellman.andrew.ann.core.math.scale;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class ScalerByRowTaskPoolTest {

	private ScalerByRowTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<ScalerByRowTask> objectPool = new GenericObjectPool<>(new ScalerByRowTaskFactory());
		taskPool = new ScalerByRowTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<ScalerByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
