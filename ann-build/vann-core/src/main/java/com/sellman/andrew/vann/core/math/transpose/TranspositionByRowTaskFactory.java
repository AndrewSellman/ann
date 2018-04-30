package com.sellman.andrew.vann.core.math.transpose;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class TranspositionByRowTaskFactory extends BasePooledObjectFactory<TranspositionByRowTask> {

	@Override
	public TranspositionByRowTask create() {
		return new TranspositionByRowTask();
	}

	@Override
	public PooledObject<TranspositionByRowTask> wrap(TranspositionByRowTask task) {
		return new DefaultPooledObject<TranspositionByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<TranspositionByRowTask> po) {
		po.getObject().recycle();
	}

}
