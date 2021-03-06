package com.sellman.andrew.vann.core.math.sum;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SummationByRowTaskFactory extends BasePooledObjectFactory<SummationByRowTask> {

	@Override
	public SummationByRowTask create() {
		return new SummationByRowTask();
	}

	@Override
	public PooledObject<SummationByRowTask> wrap(SummationByRowTask task) {
		return new DefaultPooledObject<SummationByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<SummationByRowTask> po) {
		po.getObject().recycle();
	}

}
