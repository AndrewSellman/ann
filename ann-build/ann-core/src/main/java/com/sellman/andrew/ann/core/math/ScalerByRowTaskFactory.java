package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class ScalerByRowTaskFactory extends BasePooledObjectFactory<ScalerByRowTask> {

	@Override
	public ScalerByRowTask create() throws Exception {
		return new ScalerByRowTask();
	}

	@Override
	public PooledObject<ScalerByRowTask> wrap(ScalerByRowTask task) {
		return new DefaultPooledObject<ScalerByRowTask>(task);
	}

	public void passivateObject(PooledObject<ScalerByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
