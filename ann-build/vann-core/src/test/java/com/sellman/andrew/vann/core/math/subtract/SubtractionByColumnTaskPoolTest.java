package com.sellman.andrew.vann.core.math.subtract;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.math.subtract.SubtractionByColumnTask;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByColumnTaskFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionByColumnTaskPool;

public class SubtractionByColumnTaskPoolTest {

	private SubtractionByColumnTaskPool taskPool;
	
	@Before
	public void prepareTest() {
		GenericObjectPool<SubtractionByColumnTask> objectPool = new GenericObjectPool<>(new SubtractionByColumnTaskFactory());
		taskPool = new SubtractionByColumnTaskPool(objectPool);
	}
	
	@Test
	public void getNewBorrowList() {
		List<SubtractionByColumnTask> tasks = taskPool.getNewBorrowList(2);
		assertTrue(tasks.isEmpty());
	}
	
}
