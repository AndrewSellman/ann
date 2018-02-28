package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class StandardMultiplicationByRowTaskFactory extends BasePooledObjectFactory<StandardMultiplicationByRowTask> {

	@Override
	public StandardMultiplicationByRowTask create() throws Exception {
		return new StandardMultiplicationByRowTask();
	}

	@Override
	public PooledObject<StandardMultiplicationByRowTask> wrap(StandardMultiplicationByRowTask task) {
		return new DefaultPooledObject<StandardMultiplicationByRowTask>(task);
	}

	public void passivateObject(PooledObject<StandardMultiplicationByRowTask> po) throws Exception {
		po.getObject().recycle();
	}
	
}
