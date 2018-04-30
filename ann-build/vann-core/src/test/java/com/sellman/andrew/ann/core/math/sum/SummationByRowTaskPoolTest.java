package com.sellman.andrew.ann.core.math.sum;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

public class SummationByRowTaskPoolTest {

	private SummationByRowTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<SummationByRowTask> objectPool = new GenericObjectPool<>(new SummationByRowTaskFactory());
		taskPool = new SummationByRowTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<SummationByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
