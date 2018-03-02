package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.ParallelizableOperation1;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation1<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation1<R, C> {
	private final ParallelizableOperation1Advisor advisor;

	protected AdvisableParallelizableOperation1(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final Matrix doShellParallelOp(final Matrix a, final Matrix b) {
		return doParallelOp(a, b);
	}

	public final Matrix doShellSequentialOp(final Matrix a, final Matrix b) {
		return doSequentialOp(a, b);
	}

	@Override
	protected boolean doAsParallelOp(final Matrix a, final Matrix b) {
		return advisor.doAsParrallelOp(this, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

}
