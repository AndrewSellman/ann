package com.sellman.andrew.vann.core.math.scale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;

class ScalerByColumnTaskPool extends AbstractOperationByColumnTaskPool<ScalerByColumnTask> {

	public ScalerByColumnTaskPool(GenericObjectPool<ScalerByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<ScalerByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<ScalerByColumnTask>(howManyTasksToBorrow);
	}

}
