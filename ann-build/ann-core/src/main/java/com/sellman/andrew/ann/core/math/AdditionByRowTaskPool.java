package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class AdditionByRowTaskPool extends AbstractOperationByRowTaskPool<AdditionByRowTask> {

	public AdditionByRowTaskPool(GenericObjectPool<AdditionByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<AdditionByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<AdditionByRowTask>(howManyTasksToBorrow);
	}

}
