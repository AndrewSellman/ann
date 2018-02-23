package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class StandardMultiplyByColumnTaskFactory extends BasePooledObjectFactory<StandardMultiplyByColumnTask> {

	@Override
	public StandardMultiplyByColumnTask create() throws Exception {
		return new StandardMultiplyByColumnTask();
	}

	@Override
	public PooledObject<StandardMultiplyByColumnTask> wrap(StandardMultiplyByColumnTask task) {
		return new DefaultPooledObject<StandardMultiplyByColumnTask>(task);
	}

	public void passivateObject(PooledObject<StandardMultiplyByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}
	
}
