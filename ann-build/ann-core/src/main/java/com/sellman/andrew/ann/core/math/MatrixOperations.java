package com.sellman.andrew.ann.core.math;

public interface MatrixOperations {
	
	public Matrix multiply(Matrix left, Matrix right);
	
	public Vector multiply(Vector left, Matrix right);
	
	public Matrix multiply(Matrix left, Vector right);
	
	public Vector hadamard(Vector a, Vector b);

	public Matrix add(Matrix a, Matrix b);

	public Vector add(Vector a, Vector b);

	public Matrix subtract(Matrix left, Matrix right);

	public Vector subtract(Vector left, Vector right);
	
	public Matrix scale(Matrix a, Function f);

	public Vector scale(Vector a, Function f);
	
	public Matrix transpose(Matrix a);
	
}
