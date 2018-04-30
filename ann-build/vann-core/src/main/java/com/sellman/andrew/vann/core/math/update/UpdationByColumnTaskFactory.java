package com.sellman.andrew.vann.core.math.update;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class UpdationByColumnTaskFactory extends BasePooledObjectFactory<UpdationByColumnTask> {

	@Override
	public UpdationByColumnTask create() {
		return new UpdationByColumnTask();
	}

	@Override
	public PooledObject<UpdationByColumnTask> wrap(UpdationByColumnTask task) {
		return new DefaultPooledObject<UpdationByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<UpdationByColumnTask> po) {
		po.getObject().recycle();
	}

}
