package com.sellman.andrew.vann.core.math.advice;

import java.util.concurrent.ThreadLocalRandom;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;

public abstract class AdvisableParallelizableOperationExperiment {
	protected static final int MAX_ROWS = 500;
	protected static final int MAX_COLUMNS = 500;
	protected static final int TESTS_PER_ROUND = 100;
	protected TaskService taskService;

	protected final void outputHeaders() {
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

	protected final void outputDetails(final int rowCount, final int columnCount, final String best, final double avererageSequential, final double avererageParallel, final double averageAdvised, final double advisedDifference) {
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
	}

	protected final Matrix buildMatrixA(final int rowCount, final int columnCount) {
		Matrix m = getNewMatrixA(rowCount, columnCount);
		return populate(m);
	}

	protected Matrix getNewMatrixA(final int rowCount, final int columnCount) {
		return new Matrix(rowCount, columnCount);
	}
	
	protected final Matrix buildMatrixB(final int rowCount, final int columnCount) {
		Matrix m = getNewMatrixB(rowCount, columnCount);
		return populate(m);
	}

	protected Matrix getNewMatrixB(final int rowCount, final int columnCount) {
		return new Matrix(rowCount, columnCount);
	}

	protected final Matrix populate(final Matrix m) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, ThreadLocalRandom.current().nextDouble());
			}
		}
		return m;
	}

	private String format(final double d) {
		return String.format("% 9.3f", d);
	}

	private String formatColumnCount(final int columnCount) {
		return String.format("%5d", columnCount);
	}

	private String formatRowCount(final int rowCount) {
		return String.format("%5d", rowCount);
	}

	private String lpad(String s, final int size) {
		while (s.length() < size) {
			s = " " + s;
		}

		return s;
	}

}
