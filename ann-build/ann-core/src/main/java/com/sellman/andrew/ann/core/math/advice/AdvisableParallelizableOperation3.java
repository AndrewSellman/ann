package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.ParallelizableOperation3;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation3<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation3<R, C> {
	private final ParallelizableOperation3Advisor advisor;

	protected AdvisableParallelizableOperation3(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation3Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final Matrix doShellParallelOp(final Matrix m) {
		return doParallelOp(m);
	}

	public final Matrix doShellSequentialOp(final Matrix m) {
		return doSequentialOp(m);
	}

	protected final boolean doAsParallelOp(final Matrix m) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount());
	}

}
