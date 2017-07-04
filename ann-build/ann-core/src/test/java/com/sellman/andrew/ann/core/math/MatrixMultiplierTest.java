package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixMultiplierTest {
	private static final Matrix M1X2 = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private MatrixMultiplier multiplier;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().build();
		multiplier = new MatrixMultiplier(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void multiply1by2MatrixWith2by1Matrix() {
		Matrix result = multiplier.multiply(M1X2, M2X1);
		assertEquals(1, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(11.0, result.getValue(0, 0), 0.0);
	}

	@Test
	public void multiply1by2MatrixWith2by3Matrix() {
		Matrix result = multiplier.multiply(M1X2, M2X3);
		assertEquals(1, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(9.0, result.getValue(0, 0), 0.0);
		assertEquals(12.0, result.getValue(0, 1), 0.0);
		assertEquals(15.0, result.getValue(0, 2), 0.0);
	}

	@Test
	public void multiply2byMatrix1With1by2Matrix() {
		Matrix result = multiplier.multiply(M2X1, M1X2);
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(3.0, result.getValue(0, 0), 0.0);
		assertEquals(6.0, result.getValue(0, 1), 0.0);
		assertEquals(4.0, result.getValue(1, 0), 0.0);
		assertEquals(8.0, result.getValue(1, 1), 0.0);
	}

	@Test
	public void multiply2by3MatrixWith3by2Matrix() {
		Matrix result = multiplier.multiply(M2X3, M3X2);
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(58.0, result.getValue(0, 0), 0.0);
		assertEquals(64.0, result.getValue(0, 1), 0.0);
		assertEquals(139.0, result.getValue(1, 0), 0.0);
		assertEquals(154.0, result.getValue(1, 1), 0.0);
	}

	@Test
	public void multiply3by2MatrixWith2by3Matrix() {
		Matrix result = multiplier.multiply(M3X2, M2X3);
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

	@Ignore
	@Test
	public void roughComparisonBetweenMultiThreadedAndSingleThreadedMultiplicationOfLargerMatrices() {
		System.out.print("Initializing...");
		RandomInitializer initializer = new RandomInitializer();
		Matrix a = new Matrix(25, 1000);
		initializer.init(a, 1, 2);
		Matrix b = new Matrix(1000, 5000);
		initializer.init(b, 0, 1);
		System.out.println("complete.");

		System.out.println("Starting multi-threaded multiplication...");
		long start = System.currentTimeMillis();
		Matrix c = multiplier.multiply(a, b);
		long end = System.currentTimeMillis();
		System.out.println("Multi-threaded took: " + (end - start) + " ms");

		c = new Matrix(a.getRowCount(), b.getColumnCount());
		System.out.println("Starting single-threaded multiplication...");
		start = System.currentTimeMillis();
		for (int rowIndex = 0; rowIndex < a.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < b.getColumnCount(); columnIndex++) {
				double result = 0.0;
				for (int k = 0; k < a.getColumnCount(); k++) {
					result += a.getValue(rowIndex, k) * b.getValue(k, columnIndex);
				}
				c.setValue(rowIndex, columnIndex, result);
			}
		}
		end = System.currentTimeMillis();
		System.out.println("Single thread took: " + (end - start) + " ms");
	}

}
