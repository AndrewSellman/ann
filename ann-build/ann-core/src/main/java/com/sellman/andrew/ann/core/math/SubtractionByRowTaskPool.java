package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

class SubtractionByRowTaskPool extends AbstractOperationByRowTaskPool<SubtractionByRowTask> {

	public SubtractionByRowTaskPool(GenericObjectPool<SubtractionByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<SubtractionByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<SubtractionByRowTask>(howManyTasksToBorrow);
	}

}
