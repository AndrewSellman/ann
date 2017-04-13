package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.Matrix;
import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixMathTest {
	private static final Matrix m1x2 = new Matrix(new double[][] { { 1, 2 } });
	private static final Matrix m2x1 = new Matrix(new double[][] { { 3 }, { 4 } });
	private static final Matrix m2x3 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
	private static final Matrix m3x2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private MatrixMath math;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().highPriority().build();
		math = new MatrixMath(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void multiply1by2with2by1() {
		Matrix result = math.multiply(m1x2, m2x1);
		assertEquals(1, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(11.0, result.getValue(0, 0), 0.0);
	}

	@Test
	public void multiply1by2with2by3() {
		Matrix result = math.multiply(m1x2, m2x3);
		assertEquals(1, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(9.0, result.getValue(0, 0), 0.0);
		assertEquals(12.0, result.getValue(0, 1), 0.0);
		assertEquals(15.0, result.getValue(0, 2), 0.0);
	}

	@Test
	public void multiply2by1with1by2() {
		Matrix result = math.multiply(m2x1, m1x2);
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(3.0, result.getValue(0, 0), 0.0);
		assertEquals(6.0, result.getValue(0, 1), 0.0);
		assertEquals(4.0, result.getValue(1, 0), 0.0);
		assertEquals(8.0, result.getValue(1, 1), 0.0);
	}

	@Test
	public void multiply2by3with3by2() {
		Matrix result = math.multiply(m2x3, m3x2);
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(58.0, result.getValue(0, 0), 0.0);
		assertEquals(64.0, result.getValue(0, 1), 0.0);
		assertEquals(139.0, result.getValue(1, 0), 0.0);
		assertEquals(154.0, result.getValue(1, 1), 0.0);
	}

	@Test
	public void multiply3by2with2by3() {
		Matrix result = math.multiply(m3x2, m2x3);
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

}
