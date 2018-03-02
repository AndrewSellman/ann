package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.ParallelizableOperation5;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation5<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation5<R, C> {
	private final ParallelizableOperation5Advisor advisor;
	
	protected AdvisableParallelizableOperation5(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation5Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final void doShellParallelOp(final Matrix source, final Matrix target) {
		doParallelOp(source, target);
	}

	public final void doShellSequentialOp(final Matrix source, final Matrix target) {
		doSequentialOp(source, target);
	}

	@Override
	protected final boolean doAsParallelOp(final Matrix source) {
		return advisor.doAsParrallelOp(this, source.getRowCount(), source.getColumnCount());
	}

}
