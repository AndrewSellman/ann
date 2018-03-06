package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;
import com.sellman.andrew.ann.core.concurrent.TaskPool;
import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationTask;

public abstract class ParallelizableOperation<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> implements AutoCloseable {
	private final TaskService taskService;
	private final AbstractOperationByRowTaskPool<R> opByRowTaskPool;
	private final AbstractOperationByColumnTaskPool<C> opByColumnTaskPool;

	protected ParallelizableOperation(final TaskService taskService, AbstractOperationByRowTaskPool<R> opByRowTaskPool, AbstractOperationByColumnTaskPool<C> opByColumnTaskPool) {
		this.taskService = taskService;
		this.opByRowTaskPool = opByRowTaskPool;
		this.opByColumnTaskPool = opByColumnTaskPool;
	}

	protected void populateTask(final R task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Function f, Matrix matrixTarget, Vector vectorTarget, int rowIndex) {
		populateTask(task, taskGroup, a, b, f, matrixTarget, vectorTarget);
		task.setRowIndex(rowIndex);
	}

	protected final void populateTask(final C task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Function f, Matrix matrixTarget, Vector vectorTarget, int columnIndex) {
		populateTask(task, taskGroup, a, b, f, matrixTarget, vectorTarget);
		task.setColumnIndex(columnIndex);
	}

	protected final void runTasks(final List<? extends AbstractTask> tasks) {
		taskService.runTasks(tasks);
	}

	protected final Matrix runTasksAndGetTarget(final List<? extends AbstractTask> tasks, final Matrix target) {
		runTasks(tasks);
		return target;
	}

	protected final Vector runTasksAndReturnTarget(final List<? extends AbstractTask> tasks, final Vector target) {
		runTasks(tasks);
		return target;
	}

	protected final <T extends AbstractOperationTask> void recycle(final TaskPool<T> taskPool, final List<T> tasksToRecycle) {
		if (tasksToRecycle != null) {
			taskPool.recycle(tasksToRecycle);
		}
	}

	protected AbstractOperationByRowTaskPool<R> getOperationByRowTaskPool() {
		return opByRowTaskPool;
	}

	protected AbstractOperationByColumnTaskPool<C> getOperationByColumnTaskPool() {
		return opByColumnTaskPool;
	}

	@Override
	public void close() {
		System.out.println("Closing " + toString() + "...");
		getOperationByRowTaskPool().close();
		getOperationByColumnTaskPool().close();
		System.out.println("Closed " + toString());
	}

	private final void populateTask(final AbstractOperationTask task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Function f, Matrix matrixTarget, Vector vectorTarget) {
		task.setTaskGroup(taskGroup);
		task.setMatrixA(a);
		task.setMatrixB(b);
		task.setFunction(f);
		task.setMatrixTarget(matrixTarget);
		task.setVectorTarget(vectorTarget);
	}

}
