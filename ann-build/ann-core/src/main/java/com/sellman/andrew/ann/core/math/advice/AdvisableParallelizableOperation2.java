package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.ParallelizableOperation2;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation2<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation2<R, C> {
	private final ParallelizableOperation2Advisor advisor;

	protected AdvisableParallelizableOperation2(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation2Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public final Matrix doShellSequentialOp(final Matrix m, final Function f) {
		return doSequentialOp(m, f);
	}

	public Matrix doShellParallelOp(final Matrix m, final Function f) {
		return doParallelOp(m, f);
	}

	@Override
	protected boolean doAsParallelOp(final Matrix m, final Function f) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount(), f);
	}

}
