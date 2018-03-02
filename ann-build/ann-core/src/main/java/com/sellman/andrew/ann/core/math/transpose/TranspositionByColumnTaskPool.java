package com.sellman.andrew.ann.core.math.transpose;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;

class TranspositionByColumnTaskPool extends AbstractOperationByColumnTaskPool<TranspositionByColumnTask> {

	public TranspositionByColumnTaskPool(GenericObjectPool<TranspositionByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<TranspositionByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<TranspositionByColumnTask>(howManyTasksToBorrow);
	}

}
