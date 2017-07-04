package com.sellman.andrew.ann.core.math;

class MatrixOperationsImpl implements MatrixOperations {
	private final MatrixMultiplier multiplier;
	private final MatrixAdder adder;
	private final MatrixSubtractor subtractor;
	private final MatrixScaler scaler;
	private final MatrixManipulator manipulator;
	private final MatrixAverager averager;
	
	public MatrixOperationsImpl(MatrixMultiplier multiplier, MatrixAdder adder, MatrixSubtractor subtractor, MatrixScaler scaler, MatrixManipulator manipulator, MatrixAverager averager) {
		this.multiplier = multiplier;
		this.scaler = scaler;
		this.adder = adder;
		this.subtractor = subtractor;
		this.manipulator = manipulator;
		this.averager = averager;
	}
	
	@Override
	public Matrix multiply(final Matrix left, final Matrix right) {
		return multiplier.multiply(left, right);
	}
	
	@Override
	public Vector multiply(final Matrix m, final Vector v) {
		return multiplier.multiply(m, v);
	}

	@Override
	public Matrix hadamard(final Matrix a, final Matrix b) {
		return multiplier.hadamard(a, b);
	}

	@Override
	public Vector hadamard(final Vector a, final Vector b) {
		return multiplier.hadamard(a, b);
	}

	@Override
	public Matrix add(final Matrix a, final Matrix b) {
		return adder.add(a, b);
	}

	@Override
	public Matrix add(final Matrix a, final Vector b) {
		return adder.add(a, b);
	}

	@Override
	public Vector add(final Vector a, final Vector b) {
		return adder.add(a, b);
	}

	@Override
	public Matrix subtract(final Matrix left, final Matrix right) {
		return subtractor.subtract(left, right);
	}

	@Override
	public Matrix scale(final Matrix a, final Function f) {
		return scaler.scale(a, f);
	}

	@Override
	public Matrix transpose(final Matrix a) {
		return manipulator.transpose(a);
	}

	@Override
	public Matrix transpose(final Vector a) {
		return manipulator.transpose(a);
	}

	@Override
	public void update(final Matrix source, final Matrix target) {
		manipulator.update(source, target);
	}

	@Override
	public Matrix average(final Matrix a) {
		return averager.average(a);
	}

	@Override
	public void update(final Vector source, final Vector target) {
		manipulator.update(source, target);
	}

	@Override
	public Vector subtract(final Vector left, final Vector right) {
		return subtractor.subtract(left, right);
	}

	@Override
	public Vector scale(final Vector v, final Function f) {
		return scaler.scale(v, f);
	}

	@Override
	public double sum(final Matrix m) {
		return adder.sum(m);
	}

	@Override
	public Matrix absolute(final Matrix m) {
		return scaler.scale(m, x -> Math.abs(x));
	}

}
