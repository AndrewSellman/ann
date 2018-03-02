package com.sellman.andrew.ann.core.math.transpose;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class TranspositionByColumnTaskFactory extends BasePooledObjectFactory<TranspositionByColumnTask> {

	@Override
	public TranspositionByColumnTask create() throws Exception {
		return new TranspositionByColumnTask();
	}

	@Override
	public PooledObject<TranspositionByColumnTask> wrap(TranspositionByColumnTask task) {
		return new DefaultPooledObject<TranspositionByColumnTask>(task);
	}

	public void passivateObject(PooledObject<TranspositionByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
