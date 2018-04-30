package com.sellman.andrew.ann.core.math.scale;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class ScalerByRowTaskFactory extends BasePooledObjectFactory<ScalerByRowTask> {

	@Override
	public ScalerByRowTask create() {
		return new ScalerByRowTask();
	}

	@Override
	public PooledObject<ScalerByRowTask> wrap(ScalerByRowTask task) {
		return new DefaultPooledObject<ScalerByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<ScalerByRowTask> po) {
		po.getObject().recycle();
	}

}
