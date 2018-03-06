package com.sellman.andrew.ann.core.math.add;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class AdditionByColumnTaskFactory extends BasePooledObjectFactory<AdditionByColumnTask> {

	@Override
	public AdditionByColumnTask create() throws Exception {
		return new AdditionByColumnTask();
	}

	@Override
	public PooledObject<AdditionByColumnTask> wrap(AdditionByColumnTask task) {
		return new DefaultPooledObject<AdditionByColumnTask>(task);
	}

	public void passivateObject(PooledObject<AdditionByColumnTask> po) throws Exception {
		po.getObject().recycle();
	}

}
