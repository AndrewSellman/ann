package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class StandardMultiplicationByColumnTaskPool extends AbstractOperationByColumnTaskPool<StandardMultiplicationByColumnTask> {

	public StandardMultiplicationByColumnTaskPool(GenericObjectPool<StandardMultiplicationByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<StandardMultiplicationByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<StandardMultiplicationByColumnTask>(howManyTasksToBorrow);
	}

}
