package com.sellman.andrew.ann.core.math.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.ann.core.math.function.FixedValueFunction;
import com.sellman.andrew.ann.core.math.function.Function;

@RunWith(MockitoJUnitRunner.class)
public class AbstractOperationTaskTest {
	private static final Matrix M = new Matrix(1, 1);
	private static final Function F = new FixedValueFunction();
	private static final Vector V = new Vector(1);

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AbstractOperationTask task;

	@Test
	public void setAndGetMatrixA() {
		assertNull(task.getMatrixA());
		task.setMatrixA(M);
		assertEquals(M, task.getMatrixA());
	}

	@Test
	public void setAndGetMatrixB() {
		assertNull(task.getMatrixB());
		task.setMatrixB(M);
		assertEquals(M, task.getMatrixB());
	}

	@Test
	public void setAndGetMatrixLeft() {
		assertNull(task.getMatrixLeft());
		task.setMatrixLeft(M);
		assertEquals(M, task.getMatrixLeft());
	}

	@Test
	public void setAndGetMatrixRight() {
		assertNull(task.getMatrixRight());
		task.setMatrixRight(M);
		assertEquals(M, task.getMatrixRight());
	}

	@Test
	public void setAndGetMatrixSource() {
		assertNull(task.getMatrixSource());
		task.setMatrixSource(M);
		assertEquals(M, task.getMatrixSource());
	}

	@Test
	public void setAndGetMatrixTarget() {
		assertNull(task.getMatrixTarget());
		task.setMatrixTarget(M);
		assertEquals(M, task.getMatrixTarget());
	}

	@Test
	public void setAndGetFunction() {
		assertNull(task.getFunction());
		task.setFunction(F);
		assertEquals(F, task.getFunction());
	}

	@Test
	public void setAndGetVectorTarget() {
		assertNull(task.getVectorTarget());
		task.setVectorTarget(V);
		assertEquals(V, task.getVectorTarget());
	}

	@Test
	public void recycle() {
		task.setMatrixA(M);
		task.setMatrixB(M);
		task.setMatrixSource(M);
		task.setMatrixTarget(M);
		task.setVectorTarget(V);
		task.setFunction(F);

		task.recycle();
		assertNull(task.getMatrixA());
		assertNull(task.getMatrixB());
		assertNull(task.getMatrixLeft());
		assertNull(task.getMatrixRight());
		assertNull(task.getMatrixSource());
		assertNull(task.getMatrixTarget());
		assertNull(task.getFunction());
		assertNull(task.getVectorTarget());
	}

}
