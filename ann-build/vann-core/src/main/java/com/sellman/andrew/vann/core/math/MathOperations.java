package com.sellman.andrew.vann.core.math;

import com.sellman.andrew.vann.core.math.function.Function;

public interface MathOperations {
	
	public Matrix multiply(final InspectableMatrix left, final InspectableMatrix right);
	
	public ColumnVector multiply(final Matrix left, final ColumnVector right);
	
	public Matrix multiply(final ColumnVector left, final Matrix right);
	
	public Matrix hadamard(final InspectableMatrix a, final InspectableMatrix b);
	
	public ColumnVector hadamard(final ColumnVector a, final ColumnVector b);
	
	public Matrix add(final Matrix a, final Matrix b);
	
	public ColumnVector add(final ColumnVector a, final ColumnVector b);
	
	public Matrix add(final Matrix m, final ColumnVector v);
	
	public Matrix add(final Matrix m, final RowVector v);
	
	public Matrix subtract(final InspectableMatrix left, final InspectableMatrix right);
	
	public ColumnVector subtract(final ColumnVector left, final ColumnVector right);
	
	public ColumnVector subtract(final ColumnVector left, final InspectableMatrix right);

	public RowVector subtract(final RowVector left, final InspectableMatrix right);
	
	public Matrix scale(final InspectableMatrix m, final Function f);

	public ColumnVector scale(final ColumnVector v, final Function f);
	
	public Matrix transpose(final InspectableMatrix m);
	
	public Matrix transpose(final ColumnVector v);

	public void update(final Matrix source, final Matrix target);
	
	public void update(final ColumnVector source, final ColumnVector target);
	
	public void update(final RowVector source, final RowVector target);
	
	public double sum(final InspectableMatrix m);
	
	public double sum(final ColumnVector v);
	
	public Matrix absolute(final Matrix m);
	
	public ColumnVector absolute(final ColumnVector v);
	
}
