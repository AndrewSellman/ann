package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class MatrixManipulatorTest {
	private static final Matrix M1X1 = new Matrix(new double[][] { { 1 } });
	private static final Matrix M1X2 = new Matrix(new double[][] { { 3, 4 } });
	private static final Matrix M2X1 = new Matrix(new double[][] { { 5 }, { 6 } });
	private static final Matrix M2X2 = new Matrix(new double[][] { { 1, 2 }, { 3, 4 } });
	private static final Matrix M2X3 = new Matrix(new double[][] { { 7, 8, 9 }, { 10, 11, 12 } });
	private static final Matrix M3X2 = new Matrix(new double[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });

	private MatrixManipulator manipulator;
	private TaskService taskService;

	@Before
	public void prepareTest() {
		taskService = new TaskServiceBuilder().normalPriority().build();
		manipulator = new MatrixManipulator(taskService);
	}

	@After
	public void completeTest() throws Exception {
		taskService.close();
	}

	@Test
	public void transpose3by2atrix() {
		Matrix result = manipulator.transpose(M3X2);
		assertEquals(2, result.getRowCount());
		assertEquals(3, result.getColumnCount());
		assertEquals(7.0, result.getValue(0, 0), 0.0);
		assertEquals(9.0, result.getValue(0, 1), 0.0);
		assertEquals(11.0, result.getValue(0, 2), 0.0);
		assertEquals(8.0, result.getValue(1, 0), 0.0);
		assertEquals(10.0, result.getValue(1, 1), 0.0);
		assertEquals(12.0, result.getValue(1, 2), 0.0);
	}

	@Test
	public void transpose2by3atrix() {
		Matrix result = manipulator.transpose(M2X3);
		assertEquals(3, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(7.0, result.getValue(0, 0), 0.0);
		assertEquals(10.0, result.getValue(0, 1), 0.0);
		assertEquals(8.0, result.getValue(1, 0), 0.0);
		assertEquals(11.0, result.getValue(1, 1), 0.0);
		assertEquals(9.0, result.getValue(2, 0), 0.0);
		assertEquals(12.0, result.getValue(2, 1), 0.0);
	}

	@Test
	public void transpose2by2Matrix() {
		Matrix result = manipulator.transpose(M2X2);
		assertEquals(2, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(1.0, result.getValue(0, 0), 0.0);
		assertEquals(3.0, result.getValue(0, 1), 0.0);
		assertEquals(2.0, result.getValue(1, 0), 0.0);
		assertEquals(4.0, result.getValue(1, 1), 0.0);
	}

	@Test
	public void transpose2by1Matrix() {
		Matrix result = manipulator.transpose(M2X1);
		assertEquals(1, result.getRowCount());
		assertEquals(2, result.getColumnCount());
		assertEquals(5.0, result.getValue(0, 0), 0.0);
		assertEquals(6.0, result.getValue(0, 1), 0.0);
	}

	@Test
	public void transpose1by2Matrix() {
		Matrix result = manipulator.transpose(M1X2);
		assertEquals(2, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(3.0, result.getValue(0, 0), 0.0);
		assertEquals(4.0, result.getValue(1, 0), 0.0);
	}

	@Test
	public void transpose1by1Matrix() {
		Matrix result = manipulator.transpose(M1X1);
		assertEquals(1, result.getRowCount());
		assertEquals(1, result.getColumnCount());
		assertEquals(1.0, result.getValue(0, 0), 0.0);
	}

}
