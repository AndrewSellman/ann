package com.sellman.andrew.vann.core.math.subtract;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

class SubtractionByRowTaskPool extends AbstractOperationByRowTaskPool<SubtractionByRowTask> {

	public SubtractionByRowTaskPool(GenericObjectPool<SubtractionByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<SubtractionByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<SubtractionByRowTask>(howManyTasksToBorrow);
	}

}
