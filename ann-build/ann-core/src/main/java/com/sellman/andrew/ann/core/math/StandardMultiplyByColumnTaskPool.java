package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class StandardMultiplyByColumnTaskPool extends TaskPool<StandardMultiplyByColumnTask> {

	public StandardMultiplyByColumnTaskPool(GenericObjectPool<StandardMultiplyByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<StandardMultiplyByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<StandardMultiplyByColumnTask>(howManyTasksToBorrow);
	}

}
