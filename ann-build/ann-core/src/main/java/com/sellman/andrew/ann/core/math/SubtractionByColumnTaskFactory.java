package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SubtractionByColumnTaskFactory extends BasePooledObjectFactory<SubtractionByColumnTask> {

	@Override
	public SubtractionByColumnTask create() throws Exception {
		return new SubtractionByColumnTask();
	}

	@Override
	public PooledObject<SubtractionByColumnTask> wrap(SubtractionByColumnTask task) {
		return new DefaultPooledObject<SubtractionByColumnTask>(task);
	}

	public void passivateObject(PooledObject<SubtractionByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
