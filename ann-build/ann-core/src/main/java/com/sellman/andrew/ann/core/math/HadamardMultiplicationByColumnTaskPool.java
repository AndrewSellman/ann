package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class HadamardMultiplicationByColumnTaskPool extends AbstractOperationByColumnTaskPool<HadamardMultiplicationByColumnTask> {

	public HadamardMultiplicationByColumnTaskPool(GenericObjectPool<HadamardMultiplicationByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<HadamardMultiplicationByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<HadamardMultiplicationByColumnTask>(howManyTasksToBorrow);
	}

}
