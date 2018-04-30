package com.sellman.andrew.ann.core.math.subtract;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SubtractionByRowTaskFactory extends BasePooledObjectFactory<SubtractionByRowTask> {

	@Override
	public SubtractionByRowTask create() {
		return new SubtractionByRowTask();
	}

	@Override
	public PooledObject<SubtractionByRowTask> wrap(SubtractionByRowTask task) {
		return new DefaultPooledObject<SubtractionByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<SubtractionByRowTask> po) {
		po.getObject().recycle();
	}

}
