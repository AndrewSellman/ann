package com.sellman.andrew.vann.core.math.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

@RunWith(MockitoJUnitRunner.class)
public class AbstractOperationByRowTaskTest {
	private static final int ROW_INDEX = 1;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AbstractOperationByRowTask task;

	@Test
	public void setAndGetRowIndex() {
		assertNotEquals(ROW_INDEX, task.getRowIndex());
		task.setRowIndex(ROW_INDEX);
		assertEquals(ROW_INDEX, task.getRowIndex());
	}

	@Test
	public void recycle() {
		task.setRowIndex(ROW_INDEX);
		task.setMatrixA(new Matrix(1, 1));
		assertNotNull(task.getMatrixA());

		task.recycle();
		assertNotEquals(ROW_INDEX, task.getRowIndex());
		assertNull(task.getMatrixA());
	}

}
