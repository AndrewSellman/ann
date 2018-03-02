package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.math.function.Function;

public interface MathOperations extends AutoCloseable {
	
	public Matrix multiply(final Matrix left, final Matrix right);
	
	public Vector multiply(final Matrix left, final Vector right);
	
	public Matrix multiply(final Vector left, final Matrix right);
	
	public Matrix hadamard(final Matrix a, final Matrix b);
	
	public Vector hadamard(final Vector a, final Vector b);
	
	public Matrix add(final Matrix a, final Matrix b);
	
	public Vector add(final Vector a, final Vector b);
	
	public Matrix subtract(final Matrix left, final Matrix right);
	
	public Vector subtract(final Vector left, final Vector right);

	public Matrix scale(final Matrix m, final Function f);

	public Vector scale(final Vector v, final Function f);
	
	public Matrix transpose(final Matrix m);
	
	public Matrix transpose(final Vector v);

	public void update(final Matrix source, final Matrix target);
	
	public void update(final Vector source, final Vector target);
	
	public double sum(final Matrix m);
	
	public double sum(final Vector v);
	
	public Matrix absolute(final Matrix m);
	
	public Vector absolute(final Vector v);
	
}
