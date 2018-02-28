package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class TranspositionByColumnTaskPool extends AbstractOperationByColumnTaskPool<TranspositionByColumnTask> {

	public TranspositionByColumnTaskPool(GenericObjectPool<TranspositionByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<TranspositionByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<TranspositionByColumnTask>(howManyTasksToBorrow);
	}

}
