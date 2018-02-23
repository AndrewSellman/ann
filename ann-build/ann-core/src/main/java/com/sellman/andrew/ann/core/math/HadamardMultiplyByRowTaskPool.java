package com.sellman.andrew.ann.core.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

import com.sellman.andrew.ann.core.concurrent.TaskPool;

class HadamardMultiplyByRowTaskPool extends TaskPool<HadamardMultiplyByRowTask> {

	public HadamardMultiplyByRowTaskPool(GenericObjectPool<HadamardMultiplyByRowTask> taskPool) {
		super(taskPool);
	}

	@Override
	protected List<HadamardMultiplyByRowTask> getNewBorrowList(int howManyTasksToBorrow) {
		return new ArrayList<HadamardMultiplyByRowTask>(howManyTasksToBorrow);
	}

}
