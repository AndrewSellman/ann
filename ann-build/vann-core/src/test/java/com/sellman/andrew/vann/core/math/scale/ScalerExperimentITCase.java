package com.sellman.andrew.vann.core.math.scale;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation2;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.function.FunctionType;
import com.sellman.andrew.vann.core.math.scale.ScalerFactory;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

public class ScalerExperimentITCase {
	private static final int MAX_ROWS = 500;
	private static final int MAX_COLUMNS = 500;
	private static final int TESTS_PER_ROUND = 100;

	private AdvisableParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> scaler;
	private Function function;
	private TaskService taskService;
	private ParallelizableOperation2Advisor advisor;
	private ScalerFactory opFactory;
	private Cache<AdviceKey, Boolean> cache;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().setThreadCount(Runtime.getRuntime().availableProcessors()).build();
		cache = new CacheBuilder<AdviceKey, Boolean>("scaler").build();
		cache.clear();
		advisor = new ParallelizableOperation2Advisor(cache);
		opFactory = new ScalerFactory(taskService, advisor);
		scaler = opFactory.getOperation();

		function = FunctionType.LOGISTIC.getFunction();
	}

	@After
	public void completeTest() throws Exception {
		scaler.close();
		taskService.close();
	}

	@Test
	public void sequentialScaleComparedToParallelScale() {

		for (int rowCount = 1; rowCount <= MAX_ROWS; rowCount++) {
			for (int columnCount = 1; columnCount <= MAX_COLUMNS; columnCount++) {

				Matrix m = buildMatrix(rowCount, columnCount);
				primeAdvisor(m, function);

				long totalAdvised = 0, totalSequential = 0, totalParallel = 0;
				for (int testCount = 1; testCount <= TESTS_PER_ROUND; testCount++) {

					m = buildMatrix(rowCount, columnCount);
					totalAdvised += scale(m, function);

					m = buildMatrix(rowCount, columnCount);
					totalSequential += scaleSequential(m, function);

					m = buildMatrix(rowCount, columnCount);
					totalParallel += scaleParallel(m, function);
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

	private void primeAdvisor(Matrix m, Function f) {
		advisor.doAsParrallelOp(scaler, m.getRowCount(), m.getColumnCount(), f);
	}

	private long scaleSequential(Matrix m, Function f) {
		return scaler.getSequentialOpNanos(m, f);
	}

	private long scaleParallel(Matrix m, Function f) {
		return scaler.getParallelOpNanos(m, f);
	}

	private long scale(Matrix m, Function f) {
		long start = System.nanoTime();
		scaler.doOperation(m, f);
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
