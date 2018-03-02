package com.sellman.andrew.ann.core.math.task;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

public abstract class AbstractOperationByRowTaskPool<T extends AbstractOperationByRowTask> extends TaskPool<T> {

	public AbstractOperationByRowTaskPool(GenericObjectPool<T> pool) {
		super(pool);
	}

}
