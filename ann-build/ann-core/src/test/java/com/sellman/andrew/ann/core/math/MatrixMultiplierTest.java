package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixMultiplierTest {
	private static final Matrix M1X2 = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private Multiplier multiplier;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		multiplier = new Multiplier(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void multiply1by2MatrixWith2by1MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.multiply(M1X2, M2X1);
		assertMultiply1by2MatrixWith2by1Matrix(result);
	}

	@Test
	public void multiply1by2MatrixWith2by1MatrixSequentially() {
		Matrix result = multiplier.multiply(M1X2, M2X1);
		assertMultiply1by2MatrixWith2by1Matrix(result);
	}

	@Test
	public void multiply1by2MatrixWith2by3MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.multiply(M1X2, M2X3);
		assertMultiply1by2MatrixWith2by3Matrix(result);
	}

	@Test
	public void multiply1by2MatrixWith2by3MatrixSequentially() {
		Matrix result = multiplier.multiply(M1X2, M2X3);
		assertMultiply1by2MatrixWith2by3Matrix(result);
	}

	@Test
	public void multiply2byMatrix1With1by2MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.multiply(M2X1, M1X2);
		assertMultiply2byMatrix1With1by2Matrix(result);
	}

	@Test
	public void multiply2byMatrix1With1by2MatrixSequentially() {
		Matrix result = multiplier.multiply(M2X1, M1X2);
		assertMultiply2byMatrix1With1by2Matrix(result);
	}

	@Test
	public void multiply2by3MatrixWith3by2MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.multiply(M2X3, M3X2);
		assertMultiply2by3MatrixWith3by2Matrix(result);
	}

	@Test
	public void multiply2by3MatrixWith3by2MatrixSequentially() {
		Matrix result = multiplier.multiply(M2X3, M3X2);
		assertMultiply2by3MatrixWith3by2Matrix(result);
	}

	@Test
	public void multiply3by2MatrixWith2by3MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.multiply(M3X2, M2X3);
		assertMultiply3by2MatrixWith2by3Matrix(result);
	}

	@Test
	public void multiply3by2MatrixWith2by3MatrixSequentially() {
		Matrix result = multiplier.multiply(M3X2, M2X3);
		assertMultiply3by2MatrixWith2by3Matrix(result);
	}

	@Test
	public void hadamard1by2MatrixWith1by2MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.hadamard(M1X2, M1X2);
		assertHadamard1by2MatrixWith1by2Matrix(result);
	}

	@Test
	public void hadamard1by2MatrixWith1by2MatrixSequential() {
		Matrix result = multiplier.hadamard(M1X2, M1X2);
		assertHadamard1by2MatrixWith1by2Matrix(result);
	}

	@Test
	public void hadamard2by1MatrixWith2by1MatrixParallel() {
		ParallelTaskGate.setParrallelMatrixTasksCellCountThreshold(0);

		Matrix result = multiplier.hadamard(M2X1, M2X1);
		assertHadamard2by1MatrixWith2by1Matrix(result);
	}

	@Test
	public void hadamard2by1MatrixWith2by1MatrixSequential() {
		Matrix result = multiplier.hadamard(M2X1, M2X1);
		assertHadamard2by1MatrixWith2by1Matrix(result);
	}

	private void assertMultiply1by2MatrixWith2by1Matrix(Matrix result) {
		assertEquals(1, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(11.0, result.getValue(0, 0), 0.0);
	}

	private void assertMultiply1by2MatrixWith2by3Matrix(Matrix result) {
		assertEquals(1, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(9.0, result.getValue(0, 0), 0.0);
		assertEquals(12.0, result.getValue(0, 1), 0.0);
		assertEquals(15.0, result.getValue(0, 2), 0.0);
	}

	private void assertMultiply2byMatrix1With1by2Matrix(Matrix result) {
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(3.0, result.getValue(0, 0), 0.0);
		assertEquals(6.0, result.getValue(0, 1), 0.0);
		assertEquals(4.0, result.getValue(1, 0), 0.0);
		assertEquals(8.0, result.getValue(1, 1), 0.0);
	}

	private void assertMultiply2by3MatrixWith3by2Matrix(Matrix result) {
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(58.0, result.getValue(0, 0), 0.0);
		assertEquals(64.0, result.getValue(0, 1), 0.0);
		assertEquals(139.0, result.getValue(1, 0), 0.0);
		assertEquals(154.0, result.getValue(1, 1), 0.0);
	}

	public void assertMultiply3by2MatrixWith2by3Matrix(Matrix result) {
		assertEquals(3, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(39.0, result.getValue(0, 0), 0.0);
		assertEquals(54.0, result.getValue(0, 1), 0.0);
		assertEquals(69.0, result.getValue(0, 2), 0.0);
		assertEquals(49.0, result.getValue(1, 0), 0.0);
		assertEquals(68.0, result.getValue(1, 1), 0.0);
		assertEquals(87.0, result.getValue(1, 2), 0.0);
		assertEquals(59.0, result.getValue(2, 0), 0.0);
		assertEquals(82.0, result.getValue(2, 1), 0.0);
		assertEquals(105.0, result.getValue(2, 2), 0.0);
	}

	private void assertHadamard1by2MatrixWith1by2Matrix(Matrix result) {
		assertEquals(1, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(1.0, result.getValue(0, 0), 0.0);
		assertEquals(4.0, result.getValue(0, 1), 0.0);
	}

	private void assertHadamard2by1MatrixWith2by1Matrix(Matrix result) {
		assertEquals(2, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(9.0, result.getValue(0, 0), 0.0);
		assertEquals(16.0, result.getValue(1, 0), 0.0);
	}

}
