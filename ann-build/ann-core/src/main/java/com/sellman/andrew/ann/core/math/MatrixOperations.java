package com.sellman.andrew.ann.core.math;

public interface MatrixOperations {
	
	public Matrix multiply(final Matrix left, final Matrix right);
	
	public Vector multiply(final Matrix m, final Vector v);
	
	public Matrix hadamard(final Matrix a, final Matrix b);
	
	public Vector hadamard(final Vector a, final Vector b);
	
	public Matrix add(final Matrix a, final Matrix b);
	
	public Matrix add(final Matrix a, final Vector b);
	
	public Vector add(final Vector a, final Vector b);

	public Matrix subtract(final Matrix left, final Matrix right);

	public Vector subtract(final Vector left, final Vector right);
	
	public Matrix scale(final Matrix a, final Function f);

	public Matrix transpose(final Matrix a);
	
	public Matrix transpose(final Vector a);
	
	public void update(final Matrix source, final Matrix target);
	
	public void update(final Vector source, final Vector target);
	
	public Matrix average(final Matrix a);

	public Vector scale(Vector v, Function f);
	
	public double sum(Matrix m);
	
	public Matrix absolute(Matrix m);
	
}
