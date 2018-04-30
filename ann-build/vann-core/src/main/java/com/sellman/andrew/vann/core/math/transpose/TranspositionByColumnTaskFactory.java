package com.sellman.andrew.vann.core.math.transpose;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class TranspositionByColumnTaskFactory extends BasePooledObjectFactory<TranspositionByColumnTask> {

	@Override
	public TranspositionByColumnTask create() {
		return new TranspositionByColumnTask();
	}

	@Override
	public PooledObject<TranspositionByColumnTask> wrap(TranspositionByColumnTask task) {
		return new DefaultPooledObject<TranspositionByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<TranspositionByColumnTask> po) {
		po.getObject().recycle();
	}

}
