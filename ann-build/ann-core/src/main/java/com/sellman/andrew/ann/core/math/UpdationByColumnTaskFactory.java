package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class UpdationByColumnTaskFactory extends BasePooledObjectFactory<UpdationByColumnTask> {

	@Override
	public UpdationByColumnTask create() throws Exception {
		return new UpdationByColumnTask();
	}

	@Override
	public PooledObject<UpdationByColumnTask> wrap(UpdationByColumnTask task) {
		return new DefaultPooledObject<UpdationByColumnTask>(task);
	}

	public void passivateObject(PooledObject<UpdationByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
