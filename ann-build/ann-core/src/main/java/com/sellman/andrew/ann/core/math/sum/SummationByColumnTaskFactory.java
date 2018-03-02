package com.sellman.andrew.ann.core.math.sum;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class SummationByColumnTaskFactory extends BasePooledObjectFactory<SummationByColumnTask> {

	@Override
	public SummationByColumnTask create() throws Exception {
		return new SummationByColumnTask();
	}

	@Override
	public PooledObject<SummationByColumnTask> wrap(SummationByColumnTask task) {
		return new DefaultPooledObject<SummationByColumnTask>(task);
	}

	public void passivateObject(PooledObject<SummationByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
