package com.sellman.andrew.ann.core.math;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

public abstract class AbstractOperationByColumnTaskPool<T extends AbstractOperationByColumnTask> extends TaskPool<T> {

	public AbstractOperationByColumnTaskPool(GenericObjectPool<T> pool) {
		super(pool);
	}

	//TODO is this needed?
	public List<T> borrow(int howManyTasks) throws Exception {
		return super.borrow(howManyTasks);
	}
	
}
