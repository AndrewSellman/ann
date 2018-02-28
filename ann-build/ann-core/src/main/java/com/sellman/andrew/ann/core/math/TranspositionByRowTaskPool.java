package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class TranspositionByRowTaskPool extends AbstractOperationByRowTaskPool<TranspositionByRowTask> {

	public TranspositionByRowTaskPool(GenericObjectPool<TranspositionByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<TranspositionByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<TranspositionByRowTask>(howManyTasksToBorrow);
	}

}
