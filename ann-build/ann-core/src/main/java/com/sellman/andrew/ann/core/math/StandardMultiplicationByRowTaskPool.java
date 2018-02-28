package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class StandardMultiplicationByRowTaskPool extends AbstractOperationByRowTaskPool<StandardMultiplicationByRowTask> {

	public StandardMultiplicationByRowTaskPool(GenericObjectPool<StandardMultiplicationByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<StandardMultiplicationByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<StandardMultiplicationByRowTask>(howManyTasksToBorrow);
	}

}
