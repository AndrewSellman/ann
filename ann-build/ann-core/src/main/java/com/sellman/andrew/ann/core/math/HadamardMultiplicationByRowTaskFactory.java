package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplicationByRowTaskFactory extends BasePooledObjectFactory<HadamardMultiplicationByRowTask> {

	@Override
	public HadamardMultiplicationByRowTask create() throws Exception {
		return new HadamardMultiplicationByRowTask();
	}

	@Override
	public PooledObject<HadamardMultiplicationByRowTask> wrap(HadamardMultiplicationByRowTask task) {
		return new DefaultPooledObject<HadamardMultiplicationByRowTask>(task);
	}

	public void passivateObject(PooledObject<HadamardMultiplicationByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
