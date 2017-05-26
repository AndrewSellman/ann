package com.sellman.andrew.ann.core.math;

class MatrixOperationsImpl implements MatrixOperations {
	private final MatrixMultiplier multiplier;
	private final MatrixAdder adder;
	private final MatrixSubtractor subtractor;
	private final MatrixScaler scaler;
	private final MatrixManipulator manipulator;
	
	public MatrixOperationsImpl(MatrixMultiplier multiplier, MatrixAdder adder, MatrixSubtractor subtractor, MatrixScaler scaler, MatrixManipulator manipulator) {
		this.multiplier = multiplier;
		this.scaler = scaler;
		this.adder = adder;
		this.subtractor = subtractor;
		this.manipulator = manipulator;
	}
	
	public Matrix multiply(Matrix left, Matrix right) {
		return multiplier.multiply(left, right);
	}
	
	public Vector multiply(Vector left, Matrix right) {
		return multiplier.multiply(left, right);
	}
	
	public Matrix multiply(Matrix left, Vector right) {
		return multiplier.multiply(left, right);
	}
	
	public Vector hadamard(Vector a, Vector b) {
		return multiplier.hadamard(a, b);
	}

	public Matrix add(Matrix a, Matrix b) {
		return adder.add(a, b);
	}

	public Vector add(Vector a, Vector b) {
		return adder.add(a, b);
	}

	public Matrix subtract(Matrix left, Matrix right) {
		return subtractor.subtract(left, right);
	}

	public Vector subtract(Vector left, Vector right) {
		return subtractor.subtract(left, right);
	}
	
	public Matrix scale(Matrix a, Function f) {
		return scaler.scale(a, f);
	}

	public Vector scale(Vector a, Function f) {
		return scaler.scale(a, f);
	}
	
	public Matrix transpose(Matrix a) {
		return manipulator.transpose(a);
	}
}
