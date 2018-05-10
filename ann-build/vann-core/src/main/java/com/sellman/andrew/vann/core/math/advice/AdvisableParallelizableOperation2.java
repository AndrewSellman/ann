package com.sellman.andrew.vann.core.math.advice;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation2;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTaskPool;

public abstract class AdvisableParallelizableOperation2<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> extends ParallelizableOperation2<R, C> {
	private final ParallelizableOperation2Advisor advisor;

	protected AdvisableParallelizableOperation2(final TaskService taskService, final AbstractOperationByRowTaskPool<R> opByRowTaskPool, final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool, final ParallelizableOperation2Advisor advisor) {
		super(taskService, opByRowTaskPool, opByColumnTaskPool);
		this.advisor = advisor;
	}

	public long getSequentialOpNanos(final InspectableMatrix m, final Function f) {
		long start = System.nanoTime();
		doSequentialOp(m, f);
		return System.nanoTime() - start;
	}

	public long getParallelOpNanos(final InspectableMatrix m, final Function f) {
		long start = System.nanoTime();
		doParallelOp(m, f);
		return System.nanoTime() - start;
	}

	@Override
	protected final boolean doAsParallelOp(final InspectableMatrix m, final Function f) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount(), f);
	}

}
