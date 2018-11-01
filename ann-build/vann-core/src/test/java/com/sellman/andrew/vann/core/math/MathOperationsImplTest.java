package com.sellman.andrew.vann.core.math;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.math.MathOperationsImpl;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ParallelizableOperation1;
import com.sellman.andrew.vann.core.math.ParallelizableOperation2;
import com.sellman.andrew.vann.core.math.ParallelizableOperation3;
import com.sellman.andrew.vann.core.math.ParallelizableOperation4;
import com.sellman.andrew.vann.core.math.ParallelizableOperation5;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

@RunWith(MockitoJUnitRunner.class)
public class MathOperationsImplTest {
	private static final Double EXPECTED_SUM = 1.23;
	private MathOperationsImpl mathOps;
	private Matrix m1;
	private Matrix m2;
	private Matrix expectedMatrixResult;
	private ColumnVector v1;
	private ColumnVector v2;
	private ColumnVector expectedVectorResult;

	@Mock
	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> standardMultiplication;
	
	@Mock
	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> hadamardMultiplication;

	@Mock
	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> addition;

	@Mock
	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> subtraction;

	@Mock
	private ParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> scaler;

	@Mock
	private ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> transposition;

	@Mock
	private ParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> updation;

	@Mock
	private ParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> summation;
	
	@Mock
	private Function f;
	
	@Before
	public void prepareTest() throws Exception {
		mathOps = new MathOperationsImpl(standardMultiplication, hadamardMultiplication, addition, summation, subtraction, scaler, transposition, updation);
		
		m1 = new Matrix(1, 1);
		m2 = new Matrix(1, 1);
		expectedMatrixResult = new Matrix(1, 1);

		v1 = new ColumnVector(1);
		v2 = new ColumnVector(1);
		expectedVectorResult = new ColumnVector(1);
	}

	@Test
	public void close() throws Exception {
		mathOps.close();
		
		verify(standardMultiplication).close();
		verify(hadamardMultiplication).close();
		verify(addition).close();
		verify(subtraction).close();
		verify(scaler).close();
		verify(transposition).close();
		verify(updation).close();
		verify(summation).close();
	}

	@Test
	public void sumVector() {
		when(summation.doOperation(v1)).thenReturn(EXPECTED_SUM);

		double actualSum = mathOps.sum(v1);
		assertEquals(EXPECTED_SUM, actualSum, 0.0);
	}

	@Test
	public void sumMatrix() {
		when(summation.doOperation(m1)).thenReturn(EXPECTED_SUM);

		double actualSum = mathOps.sum(m1);
		assertEquals(EXPECTED_SUM, actualSum, 0.0);
	}

	@Test
	public void updateVector() {
		mathOps.update(v1, v2);
		verify(updation).doOperation(v1, v2);
	}

	@Test
	public void updateMatrix() {
		mathOps.update(m1, m2);
		verify(updation).doOperation(m1, m2);
	}

	@Test
	public void transposeVector() {
		when(transposition.doOperation(v1)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.transpose(v1);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void transposeMatrix() {
		when(transposition.doOperation(m1)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.transpose(m1);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void scaleVectorWithFunction() {
		when(scaler.doOperation(v1, f)).thenReturn(expectedVectorResult);

		ColumnVector actualResult = mathOps.scale(v1, f);
		assertEquals(expectedVectorResult, actualResult);
	}

	@Test
	public void scaleMatrixWithFunction() {
		when(scaler.doOperation(m1, f)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.scale(m1, f);
		assertEquals(expectedMatrixResult, actualResult);
	}

//	@Test
//	public void subtractVectorWithVector() {
//		when(subtraction.doOperation(v1, v2)).thenReturn(expectedVectorResult);
//
//		ColumnVector actualResult = mathOps.subtract(v1, v2);
//		assertEquals(expectedVectorResult, actualResult);
//	}

	@Test
	public void subtractMatrixWithMatrix() {
		when(subtraction.doOperation(m1, m2)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.subtract(m1, m2);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void addVectorWithVector() {
		when(addition.doOperation(v1, v2)).thenReturn(expectedVectorResult);

		ColumnVector actualResult = mathOps.add(v1, v2);
		assertEquals(expectedVectorResult, actualResult);
	}

	@Test
	public void addMatrixWithMatrix() {
		when(addition.doOperation(m1, m2)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.add(m1, m2);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void hadamardVectorWithVector() {
		when(hadamardMultiplication.doOperation(v1, v2)).thenReturn(expectedVectorResult);

		ColumnVector actualResult = mathOps.hadamard(v1, v2);
		assertEquals(expectedVectorResult, actualResult);
	}

	@Test
	public void hadamardMatrixWithMatrix() {
		when(hadamardMultiplication.doOperation(m1, m2)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.hadamard(m1, m2);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void multiplyVectorWithMatrix() {
		when(standardMultiplication.doOperation(v1, m1)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.multiply(v1, m1);
		assertEquals(expectedMatrixResult, actualResult);
	}

	@Test
	public void multiplyMatrixWithVector() {
		when(standardMultiplication.doOperation(m1, v1)).thenReturn(expectedVectorResult);

		ColumnVector actualResult = mathOps.multiply(m1, v1);
		assertEquals(expectedVectorResult, actualResult);
	}

	@Test
	public void multiplyMatrixWithMatrix() {
		when(standardMultiplication.doOperation(m1, m2)).thenReturn(expectedMatrixResult);

		Matrix actualResult = mathOps.multiply(m1, m2);
		assertEquals(expectedMatrixResult, actualResult);
	}

}
