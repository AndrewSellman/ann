package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class HadamardMultiplyByColumnTaskPool extends TaskPool<HadamardMultiplyByColumnTask> {

	public HadamardMultiplyByColumnTaskPool(GenericObjectPool<HadamardMultiplyByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<HadamardMultiplyByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<HadamardMultiplyByColumnTask>(howManyTasksToBorrow);
	}

}
