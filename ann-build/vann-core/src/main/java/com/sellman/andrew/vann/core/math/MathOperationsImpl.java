package com.sellman.andrew.vann.core.math;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.vann.core.math.task.AbstractOperationByRowTask;

class MathOperationsImpl implements MathOperations, AutoCloseable {
	private final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> standardMultiplication;
	private final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> hadamardMultiplication;
	private final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> addition;
	private final ParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> summation;
	private final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> subtraction;
	private final ParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> scaler;
	private final ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> transposition;
	private final ParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> updation;

	public MathOperationsImpl(final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> standardMultiplier,
			final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> hadamardMultiplier,
			final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> addition,
			final ParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> summation,
			final ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> subtraction,
			final ParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> scaler,
			final ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> transposition,
			final ParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> updation) {

		this.standardMultiplication = standardMultiplier;
		this.hadamardMultiplication = hadamardMultiplier;
		this.scaler = scaler;
		this.addition = addition;
		this.summation = summation;
		this.subtraction = subtraction;
		this.transposition = transposition;
		this.updation = updation;
	}

	@Override
	public Matrix multiply(final InspectableMatrix left, final InspectableMatrix right) {
		return standardMultiplication.doOperation(left, right);
	}

	@Override
	public ColumnVector multiply(final Matrix left, final ColumnVector right) {
		return standardMultiplication.doOperation(left, right);
	}

	@Override
	public Matrix multiply(final ColumnVector left, final Matrix right) {
		return standardMultiplication.doOperation(left, right);
	}

	@Override
	public Matrix hadamard(final InspectableMatrix a, final InspectableMatrix b) {
		return hadamardMultiplication.doOperation(a, b);
	}

	@Override
	public ColumnVector hadamard(final ColumnVector a, final ColumnVector b) {
		return hadamardMultiplication.doOperation(a, b);
	}

	@Override
	public Matrix add(final Matrix a, final Matrix b) {
		return addition.doOperation(a, b);
	}

	@Override
	public ColumnVector add(final ColumnVector a, final ColumnVector b) {
		return addition.doOperation(a, b);
	}

	@Override
	public Matrix add(final Matrix m, final ColumnVector v) {
		//TOOD optimize...
		Matrix result = new Matrix(m.getRowCount(), m.getColumnCount());
		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; r < m.getColumnCount(); c++) {
				result.setValue(r, c, m.getValue(r, c) + v.getValue(r));
			}
		}
		
		return result;
	}

	@Override
	public Matrix add(final Matrix m, final RowVector v) {
		//TOOD optimize...
		Matrix result = new Matrix(m.getRowCount(), m.getColumnCount());
		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				result.setValue(r, c, m.getValue(r, c) + v.getValue(c));
			}
		}
		
		return result;
	}

	@Override
	public ColumnVector subtract(final ColumnVector v, final InspectableMatrix m) {
		//TOOD optimize...
		ColumnVector result = new ColumnVector(m.getRowCount());
		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				result.setValue(r, v.getValue(r) - m.getValue(r, c));
			}
		}
		
		return result;
	}

	@Override
	public RowVector subtract(final RowVector v, final InspectableMatrix m) {
		//TOOD optimize...
		RowVector result = new RowVector(m.getColumnCount());
		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				result.setValue(c, v.getValue(c) - m.getValue(r, c));
			}
		}
		
		return result;
	}

	@Override
	public Matrix subtract(final InspectableMatrix left, final InspectableMatrix right) {
		return subtraction.doOperation(left, right);
	}

	@Override
	public ColumnVector subtract(final ColumnVector left, final ColumnVector right) {
		return subtraction.doOperation(left, right);
	}

	@Override
	public Matrix scale(final InspectableMatrix m, final Function f) {
		return scaler.doOperation(m, f);
	}

	@Override
	public ColumnVector scale(final ColumnVector v, final Function f) {
		return scaler.doOperation(v, f);
	}

	@Override
	public Matrix transpose(final InspectableMatrix a) {
		return transposition.doOperation(a);
	}

	@Override
	public Matrix transpose(final ColumnVector v) {
		return transposition.doOperation(v);
	}

	@Override
	public void update(final Matrix source, final Matrix target) {
		updation.doOperation(source, target);
	}

	@Override
	public void update(final ColumnVector source, final ColumnVector target) {
		updation.doOperation(source, target);
	}

	@Override
	public void update(final RowVector source, final RowVector target) {
		updation.doOperation(source, target);
	}

	@Override
	public double sum(final InspectableMatrix m) {
		return summation.doOperation(m);
	}

	@Override
	public double sum(final ColumnVector v) {
		return summation.doOperation(v);
	}

	@Override
	public Matrix absolute(final Matrix m) {
		return scaler.doOperation(m, x -> Math.abs(x));
	}

	@Override
	public ColumnVector absolute(final ColumnVector v) {
		return scaler.doOperation(v, x -> Math.abs(x));
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println(toString() + " was created.");
	}

	@Override
	@PreDestroy
	public void close() {
		System.out.println("Closing " + toString() + "...");
		standardMultiplication.close();
		hadamardMultiplication.close();
		addition.close();
		summation.close();
		subtraction.close();
		scaler.close();
		transposition.close();
		updation.close();
		System.out.println("Closed " + toString());
	}
}
