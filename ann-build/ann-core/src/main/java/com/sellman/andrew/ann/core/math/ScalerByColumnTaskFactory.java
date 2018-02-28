package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class ScalerByColumnTaskFactory extends BasePooledObjectFactory<ScalerByColumnTask> {

	@Override
	public ScalerByColumnTask create() throws Exception {
		return new ScalerByColumnTask();
	}

	@Override
	public PooledObject<ScalerByColumnTask> wrap(ScalerByColumnTask task) {
		return new DefaultPooledObject<ScalerByColumnTask>(task);
	}

	public void passivateObject(PooledObject<ScalerByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
