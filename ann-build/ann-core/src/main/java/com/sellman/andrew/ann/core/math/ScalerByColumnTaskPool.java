package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class ScalerByColumnTaskPool extends TaskPool<ScalerByColumnTask> {

	public ScalerByColumnTaskPool(GenericObjectPool<ScalerByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<ScalerByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<ScalerByColumnTask>(howManyTasksToBorrow);
	}

}
