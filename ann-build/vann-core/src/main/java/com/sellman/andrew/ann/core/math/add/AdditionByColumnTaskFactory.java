package com.sellman.andrew.ann.core.math.add;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class AdditionByColumnTaskFactory extends BasePooledObjectFactory<AdditionByColumnTask> {

	@Override
	public AdditionByColumnTask create() {
		return new AdditionByColumnTask();
	}

	@Override
	public PooledObject<AdditionByColumnTask> wrap(AdditionByColumnTask task) {
		return new DefaultPooledObject<AdditionByColumnTask>(task);
	}

	@Override
	public void passivateObject(PooledObject<AdditionByColumnTask> po) {
		po.getObject().recycle();
	}

}
