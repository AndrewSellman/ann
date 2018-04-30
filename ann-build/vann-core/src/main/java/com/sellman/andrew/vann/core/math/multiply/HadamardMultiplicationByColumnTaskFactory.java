package com.sellman.andrew.vann.core.math.multiply;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplicationByColumnTaskFactory extends BasePooledObjectFactory<HadamardMultiplicationByColumnTask> {

	@Override
	public HadamardMultiplicationByColumnTask create() {
		return new HadamardMultiplicationByColumnTask();
	}

	@Override
	public PooledObject<HadamardMultiplicationByColumnTask> wrap(HadamardMultiplicationByColumnTask task) {
		return new DefaultPooledObject<HadamardMultiplicationByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<HadamardMultiplicationByColumnTask> po) {
		po.getObject().recycle();
	}

}
