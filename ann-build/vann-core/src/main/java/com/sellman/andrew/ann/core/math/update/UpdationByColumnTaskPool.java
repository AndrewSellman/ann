package com.sellman.andrew.ann.core.math.update;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;

class UpdationByColumnTaskPool extends AbstractOperationByColumnTaskPool<UpdationByColumnTask> {

	public UpdationByColumnTaskPool(GenericObjectPool<UpdationByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<UpdationByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<UpdationByColumnTask>(howManyTasksToBorrow);
	}

}
