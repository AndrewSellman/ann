package com.sellman.andrew.ann.core.math.update;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class UpdationByRowTaskFactory extends BasePooledObjectFactory<UpdationByRowTask> {

	@Override
	public UpdationByRowTask create() {
		return new UpdationByRowTask();
	}

	@Override
	public PooledObject<UpdationByRowTask> wrap(UpdationByRowTask task) {
		return new DefaultPooledObject<UpdationByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<UpdationByRowTask> po) {
		po.getObject().recycle();
	}

}
