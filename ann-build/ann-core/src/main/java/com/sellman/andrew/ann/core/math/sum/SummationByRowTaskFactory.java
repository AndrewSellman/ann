package com.sellman.andrew.ann.core.math.sum;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SummationByRowTaskFactory extends BasePooledObjectFactory<SummationByRowTask> {

	@Override
	public SummationByRowTask create() throws Exception {
		return new SummationByRowTask();
	}

	@Override
	public PooledObject<SummationByRowTask> wrap(SummationByRowTask task) {
		return new DefaultPooledObject<SummationByRowTask>(task);
	}

	public void passivateObject(PooledObject<SummationByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
