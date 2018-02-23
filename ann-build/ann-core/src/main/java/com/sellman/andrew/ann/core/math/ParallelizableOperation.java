package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskPool;
import com.sellman.andrew.ann.core.concurrent.TaskService;

abstract class ParallelizableOperation implements AutoCloseable {
	private final TaskService taskService;
	private final ParallelizableOperationAdvisor advisor;

	protected ParallelizableOperation(final TaskService taskService, final ParallelizableOperationAdvisor advisor) {
		this.taskService = taskService;
		this.advisor = advisor;
	}

	protected final void runTasks(final List<? extends AbstractTask> tasks) {
		taskService.runTasks(tasks);
	}

	protected final Matrix runTasksAndReturnTarget(final List<? extends AbstractTask> tasks, final Matrix target) {
		runTasks(tasks);
		return target;
	}

	protected final Vector runTasksAndReturnTarget(final List<AbstractTask> tasks, final Vector target) {
		runTasks(tasks);
		return target;
	}

	protected final <T extends AbstractTask> void recycle(final TaskPool<T> taskPool, final List<T> tasksToRecycle) {
		if (tasksToRecycle != null) {
			taskPool.recycle(tasksToRecycle);
		}
	}

	protected final void populateTask(final AbstractMatrixTask task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Matrix target) {
		task.setTaskGroup(taskGroup);
		task.setMatrixA(a);
		task.setMatrixB(b);
		task.setMatrixTarget(target);
	}

	protected final boolean doAsParallelOp(final Matrix a, final Matrix b) {
		return advisor.doAsParrallelOp(this, a.getRowCount(), a.getColumnCount(), b.getRowCount(), b.getColumnCount());
	}

	protected abstract Matrix doParallelOp(final Matrix a, final Matrix b);

	protected abstract Matrix doSequentialOp(final Matrix a, final Matrix b);

}
