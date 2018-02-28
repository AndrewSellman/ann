package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class AdditionByColumnTaskPool extends AbstractOperationByColumnTaskPool<AdditionByColumnTask> {

	public AdditionByColumnTaskPool(GenericObjectPool<AdditionByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<AdditionByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<AdditionByColumnTask>(howManyTasksToBorrow);
	}

}
