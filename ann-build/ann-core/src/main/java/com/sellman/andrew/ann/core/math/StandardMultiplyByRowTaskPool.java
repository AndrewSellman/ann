package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class StandardMultiplyByRowTaskPool extends TaskPool<StandardMultiplyByRowTask> {

	public StandardMultiplyByRowTaskPool(GenericObjectPool<StandardMultiplyByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<StandardMultiplyByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<StandardMultiplyByRowTask>(howManyTasksToBorrow);
	}

}
