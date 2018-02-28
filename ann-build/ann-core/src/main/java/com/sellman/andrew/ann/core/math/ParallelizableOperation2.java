package com.sellman.andrew.ann.core.math;

import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class ParallelizableOperation2 extends ParallelizableOperation {
	private final ParallelizableOperation2Advisor advisor;

	protected ParallelizableOperation2(final TaskService taskService, final ParallelizableOperation2Advisor advisor) {
		super(taskService);
		this.advisor = advisor;
	}

	protected final void populateTask(final AbstractOperationTask task, final CountDownLatch taskGroup, final Matrix source, final Function f, Matrix target) {
		task.setTaskGroup(taskGroup);
		task.setMatrixA(source);
		task.setFunction(f);
		task.setMatrixTarget(target);
	}

	protected final boolean doAsParallelOp(final Matrix m, final Function f) {
		return advisor.doAsParrallelOp(this, m.getRowCount(), m.getColumnCount(), f);
	}

	abstract protected Matrix doParallelOp(final Matrix m, final Function f);

	abstract protected Matrix doSequentialOp(final Matrix m, final Function f);

}
