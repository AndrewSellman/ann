package com.sellman.andrew.vann.core.math.subtract;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SubtractionByColumnTaskFactory extends BasePooledObjectFactory<SubtractionByColumnTask> {

	@Override
	public SubtractionByColumnTask create() {
		return new SubtractionByColumnTask();
	}

	@Override
	public PooledObject<SubtractionByColumnTask> wrap(SubtractionByColumnTask task) {
		return new DefaultPooledObject<SubtractionByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<SubtractionByColumnTask> po) {
		po.getObject().recycle();
	}

}
