package com.sellman.andrew.vann.core.math.subtract;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTask;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTaskFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByRowTaskPool;

public class SubtractionByRowTaskPoolTest {

	private SubtractionByRowTaskPool taskPool;

	@Before
	public void prepareTest() {
		GenericObjectPool<SubtractionByRowTask> objectPool = new GenericObjectPool<>(new SubtractionByRowTaskFactory());
		taskPool = new SubtractionByRowTaskPool(objectPool);
	}

	@Test
	public void getNewBorrowList() {
		List<SubtractionByRowTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}

}
