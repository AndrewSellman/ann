package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class ScalerByRowTaskPool extends TaskPool<ScalerByRowTask> {

	public ScalerByRowTaskPool(GenericObjectPool<ScalerByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<ScalerByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<ScalerByRowTask>(howManyTasksToBorrow);
	}

}
