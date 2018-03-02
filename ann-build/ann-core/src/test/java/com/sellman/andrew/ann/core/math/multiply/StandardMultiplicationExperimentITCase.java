package com.sellman.andrew.ann.core.math.multiply;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;

public class StandardMultiplicationExperimentITCase {
	private static final int MAX_ROWS = 500;
	private static final int MAX_COLUMNS = 500;
	private static final int TESTS_PER_ROUND = 100;

	private TaskService taskService;
	private ParallelizableOperation1Advisor advisor;
	private StandardMultiplicationFactory opFactory;
	private AdvisableParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> multiplier;
	
	
	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();
		advisor = new ParallelizableOperation1Advisor();
		opFactory = new StandardMultiplicationFactory(taskService, advisor);
		multiplier = opFactory.getOperation();
	}

	@After
	public void completeTest() throws Exception {
		multiplier.close();
		taskService.close();
	}

	@Test
	public void sequentialMultiplicationComparedToParallelMultiplication() {

		for (int rowCount = 1; rowCount <= MAX_ROWS; rowCount++) {
			for (int columnCount = 1; columnCount <= MAX_COLUMNS; columnCount++) {

				Matrix left = buildMatrix(rowCount, columnCount);
				Matrix right = buildMatrix(columnCount, rowCount);
				primeAdvisor(left, right);

				long totalAdvised = 0, totalSequential = 0, totalParallel = 0;
				for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalAdvised += multiply(left, right);

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalSequential += multiplySequential(left, right);

					left = buildMatrix(rowCount, columnCount);
					right = buildMatrix(columnCount, rowCount);
					totalParallel += multiplyParallel(left, right);
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

				if (columnCount % 25 == 0) {
					headers();
				}

				StringBuilder sb = new StringBuilder();
				sb.append(formatRowCount(rowCount));
				sb.append(" ");
				sb.append(formatColumnCount(columnCount));
				sb.append("  ");
				sb.append(best);
				sb.append(format(avererageSequential));
				sb.append(format(avererageParallel));
				sb.append(format(averageAdvised));
				sb.append(format(advisedDifference));
				System.out.println(sb.toString());

				assertTrue(advisedDifference <= 500.0);
			}
		}
	}

	private void headers() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append(lpad("rows", formatRowCount(0).length()));
		sb.append(" ");
		sb.append(lpad("cols", formatColumnCount(0).length()));
		sb.append(" ");
		sb.append("best");
		sb.append("  avg seq");
		sb.append("  avg par");
		sb.append("  avg adv");
		sb.append("  adv dif");
		System.out.println(sb.toString());

	}

	private void primeAdvisor(Matrix left, Matrix right) {
		advisor.doAsParrallelOp(multiplier, left.getRowCount(), left.getColumnCount(), right.getRowCount(), right.getColumnCount());
	}

	private long multiplySequential(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.doShellSequentialOp(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private long multiplyParallel(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.doShellParallelOp(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private long multiply(Matrix left, Matrix right) {
		long start = System.nanoTime();
		multiplier.doOperation(left, right);
		long end = System.nanoTime();
		return end - start;
	}

	private Matrix buildMatrix(int rowCount, int columnCount) {
		Matrix m = new Matrix(rowCount, columnCount);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}
		return m;
	}

	private String format(double d) {
		return String.format("% 9.3f", d);
	}

	private String formatColumnCount(int columnCount) {
		return String.format("%5d", columnCount);
	}

	private String formatRowCount(int rowCount) {
		return String.format("%5d", rowCount);
	}

	private String lpad(String s, int size) {
		while (s.length() < size) {
			s = " " + s;
		}

		return s;
	}

}
