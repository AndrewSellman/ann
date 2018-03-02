package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.ParallelizableOperation4;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation4<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation4<R, C> {
	private final ParallelizableOperation4Advisor advisor;

	protected AdvisableParallelizableOperation4(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation4Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final void doShellSequentialOp(final Matrix m) {
		doSequentialOp(m);
	}

	public final void doShellParallelOp(final Matrix m) {
		doParallelOp(m);
	}

	@Override
	protected final boolean doAsParallelOp(final Matrix m) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount());
	}

}
