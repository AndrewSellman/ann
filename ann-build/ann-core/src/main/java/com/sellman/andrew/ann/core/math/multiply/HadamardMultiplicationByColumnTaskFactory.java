package com.sellman.andrew.ann.core.math.multiply;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class HadamardMultiplicationByColumnTaskFactory extends BasePooledObjectFactory<HadamardMultiplicationByColumnTask> {

	@Override
	public HadamardMultiplicationByColumnTask create() throws Exception {
		return new HadamardMultiplicationByColumnTask();
	}

	@Override
	public PooledObject<HadamardMultiplicationByColumnTask> wrap(HadamardMultiplicationByColumnTask task) {
		return new DefaultPooledObject<HadamardMultiplicationByColumnTask>(task);
	}

	public void passivateObject(PooledObject<HadamardMultiplicationByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
