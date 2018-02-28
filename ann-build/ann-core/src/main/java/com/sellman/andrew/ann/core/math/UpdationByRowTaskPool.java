package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class UpdationByRowTaskPool extends AbstractOperationByRowTaskPool<UpdationByRowTask> {

	public UpdationByRowTaskPool(GenericObjectPool<UpdationByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<UpdationByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<UpdationByRowTask>(howManyTasksToBorrow);
	}

}
