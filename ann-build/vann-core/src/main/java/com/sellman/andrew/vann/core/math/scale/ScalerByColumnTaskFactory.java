package com.sellman.andrew.vann.core.math.scale;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class ScalerByColumnTaskFactory extends BasePooledObjectFactory<ScalerByColumnTask> {

	@Override
	public ScalerByColumnTask create() {
		return new ScalerByColumnTask();
	}

	@Override
	public PooledObject<ScalerByColumnTask> wrap(ScalerByColumnTask task) {
		return new DefaultPooledObject<ScalerByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<ScalerByColumnTask> po) {
		po.getObject().recycle();
	}

}
