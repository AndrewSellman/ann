package com.sellman.andrew.vann.core.math.sum;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

class SummationByRowTaskPool extends AbstractOperationByRowTaskPool<SummationByRowTask> {

	public SummationByRowTaskPool(GenericObjectPool<SummationByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<SummationByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<SummationByRowTask>(howManyTasksToBorrow);
	}

}
