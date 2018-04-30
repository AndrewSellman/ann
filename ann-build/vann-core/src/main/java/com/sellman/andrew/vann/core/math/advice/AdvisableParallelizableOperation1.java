package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation1;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation1<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation1<R, C> {
	private final ParallelizableOperation1Advisor advisor;

	protected AdvisableParallelizableOperation1(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation1Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public long getParallelOpNanos(final Matrix a, final Matrix b) {
		long start = System.nanoTime();
		doParallelOp(a, b);
		return System.nanoTime() - start;
	}

	public long getSequentialOpNanos(final Matrix a, final Matrix b) {
		long start = System.nanoTime();
		doSequentialOp(a, b);
		return System.nanoTime() - start;
	}

	@Override
	protected final boolean doAsParallelOp(final Matrix a, final Matrix b) {
		return advisor.doAsParrallelOp(this, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

}
