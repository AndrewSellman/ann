package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation5;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation5<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation5<R, C> {
	private final ParallelizableOperation5Advisor advisor;

	protected AdvisableParallelizableOperation5(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation5Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public long getParallelOpNanos(final Matrix source, final Matrix target) {
		long start = System.nanoTime();
		doParallelOp(source, target);
		return System.nanoTime() - start;
	}

	public long getSequentialOpNanos(final Matrix source, final Matrix target) {
		long start = System.nanoTime();
		doSequentialOp(source, target);
		return System.nanoTime() - start;
	}

	@Override
	protected final boolean doAsParallelOp(final Matrix source) {
		return advisor.doAsParrallelOp(this, source.getRowCount(), source.getColumnCount());
	}

}
