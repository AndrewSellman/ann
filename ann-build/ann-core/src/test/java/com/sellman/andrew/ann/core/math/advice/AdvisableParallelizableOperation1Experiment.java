package com.sellman.andrew.ann.core.math.advice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.factory.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public abstract class AdvisableParallelizableOperation1Experiment extends AdvisableParallelizableOperationExperiment {
	private ParallelizableOperation1Advisor advisor;
	private AdvisableParallelizabeOperation1Factory opFactory;
	private AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> op;
	
	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();
		advisor = new ParallelizableOperation1Advisor();
		opFactory = getOpFactory(taskService, advisor);
		op = opFactory.getOperation();
	}

	protected abstract AdvisableParallelizabeOperation1Factory getOpFactory(TaskService taskService, ParallelizableOperation1Advisor advisor);

	@After
	public void completeTest() throws Exception {
		op.close();
		taskService.close();
	}

	@Test
	public void sequentialOpComparedToParallelOp() {
		for (int rowCount = 1; rowCount <= MAX_ROWS; rowCount++) {
			for (int columnCount = 1; columnCount <= MAX_COLUMNS; columnCount++) {

				Matrix a = buildMatrixA(rowCount, columnCount);
				Matrix b = buildMatrixB(rowCount, columnCount);
				primeAdvisor(a, b);

				long totalAdvised = 0, totalSequential = 0, totalParallel = 0;
				for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {
					
					a = buildMatrixA(rowCount, columnCount);
					b = buildMatrixB(rowCount, columnCount);

					totalAdvised += getAdvisedOpNanos(a, b);
					totalSequential += op.getSequentialOpNanos(a, b);
					totalParallel += op.getParallelOpNanos(a, b);
				}

				double avererageSequential = totalSequential * 1.0 / TESTS_PER_ROUND / 1000;
				double avererageParallel = totalParallel * 1.0 / TESTS_PER_ROUND / 1000;
				double averageAdvised = totalAdvised * 1.0 / TESTS_PER_ROUND / 1000;
				String best = "SEQ";
				double advisedDifference = averageAdvised - avererageSequential;
				if (avererageParallel < avererageSequential) {
					best = "PAR";
					advisedDifference = averageAdvised - avererageParallel;
				}

				if (columnCount % 30 == 0) {
					outputHeaders();
				}

				outputDetails(rowCount, columnCount, best, avererageSequential, avererageParallel, averageAdvised, advisedDifference);
			}
		}
	}

	private void primeAdvisor(Matrix left, Matrix right) {
		advisor.doAsParrallelOp(op, left.getRowCount(), left.getColumnCount(), right.getRowCount(), right.getColumnCount());
	}

	private long getAdvisedOpNanos(Matrix a, Matrix b) {
		long start = System.nanoTime();
		op.doOperation(a, b);
		return System.nanoTime() - start;
	}

}
