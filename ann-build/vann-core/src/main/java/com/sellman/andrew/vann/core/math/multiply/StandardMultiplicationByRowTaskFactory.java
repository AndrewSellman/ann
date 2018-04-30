package com.sellman.andrew.vann.core.math.multiply;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class StandardMultiplicationByRowTaskFactory extends BasePooledObjectFactory<StandardMultiplicationByRowTask> {

	@Override
	public StandardMultiplicationByRowTask create() {
		return new StandardMultiplicationByRowTask();
	}

	@Override
	public PooledObject<StandardMultiplicationByRowTask> wrap(StandardMultiplicationByRowTask task) {
		return new DefaultPooledObject<StandardMultiplicationByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<StandardMultiplicationByRowTask> po) {
		po.getObject().recycle();
	}
	
}
