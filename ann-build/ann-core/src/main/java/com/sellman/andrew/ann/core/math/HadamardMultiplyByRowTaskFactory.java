package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplyByRowTaskFactory extends BasePooledObjectFactory<HadamardMultiplyByRowTask> {

	@Override
	public HadamardMultiplyByRowTask create() throws Exception {
		return new HadamardMultiplyByRowTask();
	}

	@Override
	public PooledObject<HadamardMultiplyByRowTask> wrap(HadamardMultiplyByRowTask task) {
		return new DefaultPooledObject<HadamardMultiplyByRowTask>(task);
	}

	public void passivateObject(PooledObject<HadamardMultiplyByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
