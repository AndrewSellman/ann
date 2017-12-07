package com.sellman.andrew.ann.core.math;

class MatrixOperationsImpl implements MatrixOperations {
	private final MatrixMultiplier multiplier;
	private final MatrixAdder adder;
	private final MatrixSubtractor subtractor;
	private final MatrixScaler scaler;
	private final MatrixManipulator manipulator;
	
	public MatrixOperationsImpl(final MatrixMultiplier multiplier, final MatrixAdder adder, final MatrixSubtractor subtractor, final MatrixScaler scaler, final MatrixManipulator manipulator) {
		this.multiplier = multiplier;
		this.scaler = scaler;
		this.adder = adder;
		this.subtractor = subtractor;
		this.manipulator = manipulator;
	}
	
	@Override
	public Matrix multiply(final Matrix left, final Matrix right) {
		return multiplier.multiply(left, right);
	}
	
	@Override
	public Vector multiply(final Matrix left, final Vector right) {
		return multiplier.multiply(left, right);
	}

	@Override
	public Matrix multiply(final Vector left, final Matrix right) {
		return multiplier.multiply(left, right);
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
	public Vector add(final Vector a, final Vector b) {
		return adder.add(a, b);
	}

	@Override
	public Matrix subtract(final Matrix left, final Matrix right) {
		return subtractor.subtract(left, right);
	}

	@Override
	public Vector subtract(final Vector left, final Vector right) {
		return subtractor.subtract(left, right);
	}

	@Override
	public Matrix scale(final Matrix m, final Function f) {
		return scaler.scale(m, f);
	}

	@Override
	public Vector scale(final Vector v, final Function f) {
		return scaler.scale(v, f);
	}

	@Override
	public Matrix transpose(final Matrix a) {
		return manipulator.transpose(a);
	}

	@Override
	public Matrix transpose(final Vector v) {
		return manipulator.transpose(v);
	}

	@Override
	public void update(final Matrix source, final Matrix target) {
		manipulator.update(source, target);
	}

	@Override
	public void update(final Vector source, final Vector target) {
		manipulator.update(source, target);
	}

	@Override
	public double sum(final Matrix m) {
		return adder.sum(m);
	}

	@Override
	public double sum(final Vector v) {
		return adder.sum(v);
	}

	@Override
	public Matrix absolute(final Matrix m) {
		return scaler.scale(m, x -> Math.abs(x));
	}

	@Override
	public Vector absolute(final Vector v) {
		return scaler.scale(v, x -> Math.abs(x));
	}

}
