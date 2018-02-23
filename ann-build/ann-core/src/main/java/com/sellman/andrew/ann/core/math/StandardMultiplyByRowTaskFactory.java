package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class StandardMultiplyByRowTaskFactory extends BasePooledObjectFactory<StandardMultiplyByRowTask> {

	@Override
	public StandardMultiplyByRowTask create() throws Exception {
		return new StandardMultiplyByRowTask();
	}

	@Override
	public PooledObject<StandardMultiplyByRowTask> wrap(StandardMultiplyByRowTask task) {
		return new DefaultPooledObject<StandardMultiplyByRowTask>(task);
	}

	public void passivateObject(PooledObject<StandardMultiplyByRowTask> po) throws Exception {
		po.getObject().recycle();
	}
	
}
