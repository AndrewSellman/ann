package com.sellman.andrew.ann.core.math.scale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

class ScalerByRowTaskPool extends AbstractOperationByRowTaskPool<ScalerByRowTask> {

	public ScalerByRowTaskPool(GenericObjectPool<ScalerByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<ScalerByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<ScalerByRowTask>(howManyTasksToBorrow);
	}

}
