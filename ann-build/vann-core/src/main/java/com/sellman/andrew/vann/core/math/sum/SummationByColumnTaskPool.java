package com.sellman.andrew.vann.core.math.sum;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;

class SummationByColumnTaskPool extends AbstractOperationByColumnTaskPool<SummationByColumnTask> {

	public SummationByColumnTaskPool(GenericObjectPool<SummationByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<SummationByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<SummationByColumnTask>(howManyTasksToBorrow);
	}

}
