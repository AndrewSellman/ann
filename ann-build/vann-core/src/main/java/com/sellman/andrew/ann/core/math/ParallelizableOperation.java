package com.sellman.andrew.ann.core.math;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTaskPool;
import com.sellman.andrew.ann.core.math.task.AbstractOperationTask;

abstract class ParallelizableOperation<R extends AbstractOperationByRowTask, C extends AbstractOperationByColumnTask> implements AutoCloseable {
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

	protected void populateTask(final C task, final CountDownLatch taskGroup, final Matrix a, final Matrix b, Function f, Matrix matrixTarget, Vector vectorTarget, int columnIndex) {
		populateTask(task, taskGroup, a, b, f, matrixTarget, vectorTarget);
		task.setColumnIndex(columnIndex);
	}

	protected final void runTasks(final List<? extends AbstractOperationTask> tasks) {
		taskService.runTasks(tasks);
	}

	protected final Matrix runTasksAndReturnTarget(final List<? extends AbstractOperationTask> tasks, final Matrix target) {
		runTasks(tasks);
		return target;
	}

	protected final Vector runTasksAndReturnTarget(final List<? extends AbstractOperationTask> tasks, final Vector target) {
		runTasks(tasks);
		return target;
	}

	protected final AbstractOperationByRowTaskPool<R> getOperationByRowTaskPool() {
		return opByRowTaskPool;
	}

	protected final AbstractOperationByColumnTaskPool<C> getOperationByColumnTaskPool() {
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

	protected final String toStringByRow(Matrix a, Matrix b, Function f, int targetRowCount, Matrix matrixTarget, Vector vectorTarget) {
		StringBuilder sb = toString(a, b, f, matrixTarget, vectorTarget);
		sb.append("targetRowCount=");
		sb.append(targetRowCount);
		return sb.toString();
	}

	protected final String toStringByColumn(Matrix a, Matrix b, Function f, int targetColumnCount, Matrix matrixTarget, Vector vectorTarget) {
		StringBuilder sb = toString(a, b, f, matrixTarget, vectorTarget);
		sb.append("targetColumnCount=");
		sb.append(targetColumnCount);
		return sb.toString();
	}

	private StringBuilder toString(Matrix a, Matrix b, Function f, Matrix matrixTarget, Vector vectorTarget) {
		StringBuilder sb = new StringBuilder();
		if (a != null) {
			sb.append("a=");
			sb.append(a.toString());
		}

		if (b != null) {
			sb.append("b=");
			sb.append(b.toString());
		}

		if (f != null) {
			sb.append("f=");
			sb.append(f.toString());
			sb.append("\n");
		}

		if (matrixTarget != null) {
			sb.append("target=");
			sb.append(matrixTarget.toString());
		}

		if (vectorTarget != null) {
			sb.append("target=");
			sb.append(vectorTarget.toString());
		}

		return sb;
	}

}
