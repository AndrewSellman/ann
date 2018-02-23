package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplyByColumnTaskFactory extends BasePooledObjectFactory<HadamardMultiplyByColumnTask> {

	@Override
	public HadamardMultiplyByColumnTask create() throws Exception {
		return new HadamardMultiplyByColumnTask();
	}

	@Override
	public PooledObject<HadamardMultiplyByColumnTask> wrap(HadamardMultiplyByColumnTask task) {
		return new DefaultPooledObject<HadamardMultiplyByColumnTask>(task);
	}

	public void passivateObject(PooledObject<HadamardMultiplyByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
