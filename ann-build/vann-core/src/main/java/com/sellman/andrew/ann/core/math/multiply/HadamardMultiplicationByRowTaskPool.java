package com.sellman.andrew.ann.core.math.multiply;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

class HadamardMultiplicationByRowTaskPool extends AbstractOperationByRowTaskPool<HadamardMultiplicationByRowTask> {

	public HadamardMultiplicationByRowTaskPool(GenericObjectPool<HadamardMultiplicationByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<HadamardMultiplicationByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<HadamardMultiplicationByRowTask>(howManyTasksToBorrow);
	}

}
