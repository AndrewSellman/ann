package com.sellman.andrew.ann.core.math.add;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class AdditionByRowTaskFactory extends BasePooledObjectFactory<AdditionByRowTask> {

	@Override
	public AdditionByRowTask create() {
		return new AdditionByRowTask();
	}

	@Override
	public PooledObject<AdditionByRowTask> wrap(AdditionByRowTask task) {
		return new DefaultPooledObject<AdditionByRowTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<AdditionByRowTask> po) {
		po.getObject().recycle();
	}

}
