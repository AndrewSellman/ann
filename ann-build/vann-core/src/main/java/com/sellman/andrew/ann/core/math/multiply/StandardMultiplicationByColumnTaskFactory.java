package com.sellman.andrew.ann.core.math.multiply;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class StandardMultiplicationByColumnTaskFactory extends BasePooledObjectFactory<StandardMultiplicationByColumnTask> {

	@Override
	public StandardMultiplicationByColumnTask create() {
		return new StandardMultiplicationByColumnTask();
	}

	@Override
	public PooledObject<StandardMultiplicationByColumnTask> wrap(StandardMultiplicationByColumnTask task) {
		return new DefaultPooledObject<StandardMultiplicationByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<StandardMultiplicationByColumnTask> po) {
		po.getObject().recycle();
	}
	
}
