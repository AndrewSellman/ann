package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SubtractionByRowTaskFactory extends BasePooledObjectFactory<SubtractionByRowTask> {

	@Override
	public SubtractionByRowTask create() throws Exception {
		return new SubtractionByRowTask();
	}

	@Override
	public PooledObject<SubtractionByRowTask> wrap(SubtractionByRowTask task) {
		return new DefaultPooledObject<SubtractionByRowTask>(task);
	}

	public void passivateObject(PooledObject<SubtractionByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
