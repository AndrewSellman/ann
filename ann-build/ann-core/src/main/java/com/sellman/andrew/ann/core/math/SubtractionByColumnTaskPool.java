package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class SubtractionByColumnTaskPool extends AbstractOperationByColumnTaskPool<SubtractionByColumnTask> {

	public SubtractionByColumnTaskPool(GenericObjectPool<SubtractionByColumnTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<SubtractionByColumnTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<SubtractionByColumnTask>(howManyTasksToBorrow);
	}

}
