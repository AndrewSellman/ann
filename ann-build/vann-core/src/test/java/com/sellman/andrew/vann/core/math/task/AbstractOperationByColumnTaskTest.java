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
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;

@RunWith(MockitoJUnitRunner.class)
public class AbstractOperationByColumnTaskTest {
	private static final int COLUMN_INDEX = 1;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AbstractOperationByColumnTask task;

	@Test
	public void setAndGetColumnIndex() {
		assertNotEquals(COLUMN_INDEX, task.getColumnIndex());
		task.setColumnIndex(COLUMN_INDEX);
		assertEquals(COLUMN_INDEX, task.getColumnIndex());
	}

	@Test
	public void recycle() {
		task.setColumnIndex(COLUMN_INDEX);
		task.setMatrixA(new Matrix(1, 1));
		assertNotNull(task.getMatrixA());

		task.recycle();
		assertNotEquals(COLUMN_INDEX, task.getColumnIndex());
		assertNull(task.getMatrixA());
	}

}
