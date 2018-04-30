package com.sellman.andrew.vann.core.math.multiply;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplicationByRowTaskFactory extends BasePooledObjectFactory<HadamardMultiplicationByRowTask> {

	@Override
	public HadamardMultiplicationByRowTask create() {
		return new HadamardMultiplicationByRowTask();
	}

	@Override
	public PooledObject<HadamardMultiplicationByRowTask> wrap(HadamardMultiplicationByRowTask task) {
		return new DefaultPooledObject<HadamardMultiplicationByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<HadamardMultiplicationByRowTask> po) {
		po.getObject().recycle();
	}

}
