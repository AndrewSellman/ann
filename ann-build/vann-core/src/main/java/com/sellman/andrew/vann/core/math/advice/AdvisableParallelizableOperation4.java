package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation4;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation4<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation4<R, C> {
	private final ParallelizableOperation4Advisor advisor;

	protected AdvisableParallelizableOperation4(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation4Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public long getSequentialOpNanos(final InspectableMatrix m) {
		long start = System.nanoTime();
		doSequentialOp(m);
		return System.nanoTime() - start;
	}

	public long getParallelOpNanos(final InspectableMatrix m) {
		long start = System.nanoTime();
		doParallelOp(m);
		return System.nanoTime() - start;
	}

	@Override
	protected final boolean doAsParallelOp(final InspectableMatrix m) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount());
	}

}
