package com.sellman.andrew.ann.core.math;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

public abstract class AbstractOperationByRowTaskPool<T extends AbstractOperationByRowTask> extends TaskPool<T> {

	public AbstractOperationByRowTaskPool(GenericObjectPool<T> pool) {
		super(pool);
	}

//TODO is this needed?
	public List<T> borrow(int howManyTasks) throws Exception {
		return super.borrow(howManyTasks);
	}

}
