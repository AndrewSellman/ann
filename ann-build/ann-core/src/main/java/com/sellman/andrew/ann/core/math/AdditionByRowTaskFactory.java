package com.sellman.andrew.ann.core.math;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class AdditionByRowTaskFactory extends BasePooledObjectFactory<AdditionByRowTask> {

	@Override
	public AdditionByRowTask create() throws Exception {
		return new AdditionByRowTask();
	}

	@Override
	public PooledObject<AdditionByRowTask> wrap(AdditionByRowTask task) {
		return new DefaultPooledObject<AdditionByRowTask>(task);
	}

	public void passivateObject(PooledObject<AdditionByRowTask> po) throws Exception {
		po.getObject().recycle();
	}

}
