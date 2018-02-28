package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class TranspositionByRowTaskFactory extends BasePooledObjectFactory<TranspositionByRowTask> {

	@Override
	public TranspositionByRowTask create() throws Exception {
		return new TranspositionByRowTask();
	}

	@Override
	public PooledObject<TranspositionByRowTask> wrap(TranspositionByRowTask task) {
		return new DefaultPooledObject<TranspositionByRowTask>(task);
	}

	public void passivateObject(PooledObject<TranspositionByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
