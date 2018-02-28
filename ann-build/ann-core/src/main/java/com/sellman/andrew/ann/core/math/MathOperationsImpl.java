package com.sellman.andrew.ann.core.math;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

class MathOperationsImpl implements MathOperations {
	private final StandardMultiplication standardMultiplication;
	private final HadamardMultiplication hadamardMultiplication;
	private final Addition addition;
	private final Summation summation;
	private final Subtraction subtraction;
	private final Scaler scaler;
	private final Transposition transposition;
	private final Updation updation;
	
	public MathOperationsImpl(final StandardMultiplication standardMultiplier, final HadamardMultiplication hadamardMultiplier, final Addition adder, final Summation summer, final Subtraction subtractor, final Scaler scaler, final Transposition transposition, final Updation updation) {
		this.standardMultiplication = standardMultiplier;
		this.hadamardMultiplication = hadamardMultiplier;
		this.scaler = scaler;
		this.addition = adder;
		this.summation = summer;
		this.subtraction = subtractor;
		this.transposition = transposition;
		this.updation = updation;
	}
	
	@Override
	public Matrix multiply(final Matrix left, final Matrix right) {
		return standardMultiplication.doOperation(left, right);
	}
	
	@Override
	public Vector multiply(final Matrix left, final Vector right) {
		return standardMultiplication.doOperation(left, right);
	}

	@Override
	public Matrix multiply(final Vector left, final Matrix right) {
		return standardMultiplication.doOperation(left, right);
	}

	@Override
	public Matrix hadamard(final Matrix a, final Matrix b) {
		return hadamardMultiplication.doOperation(a, b);
	}

	@Override
	public Vector hadamard(final Vector a, final Vector b) {
		return hadamardMultiplication.doOperation(a, b);
	}
	
	@Override
	public Matrix add(final Matrix a, final Matrix b) {
		return addition.doOperation(a, b);
	}

	@Override
	public Vector add(final Vector a, final Vector b) {
		return addition.doOperation(a, b);
	}

	@Override
	public Matrix subtract(final Matrix left, final Matrix right) {
		return subtraction.doOperation(left, right);
	}

	@Override
	public Vector subtract(final Vector left, final Vector right) {
		return subtraction.doOperation(left, right);
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
		return transposition.doOperation(a);
	}

	@Override
	public Matrix transpose(final Vector v) {
		return transposition.doOperation(v);
	}

	@Override
	public void update(final Matrix source, final Matrix target) {
		updation.doOperation(source, target);
	}

	@Override
	public void update(final Vector source, final Vector target) {
		updation.doOperation(source, target);
	}

	@Override
	public double sum(final Matrix m) {
		return summation.doOperation(m);
	}

	@Override
	public double sum(final Vector v) {
		return summation.doOperation(v);
	}

	@Override
	public Matrix absolute(final Matrix m) {
		return scaler.scale(m, x -> Math.abs(x));
	}

	@Override
	public Vector absolute(final Vector v) {
		return scaler.scale(v, x -> Math.abs(x));
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("MathOperationsImpl " + toString() + "was created.");
	}

	@Override
	@PreDestroy
	public void close() throws Exception {
		System.out.println("Closing MathOperationsImpl " + toString() + "...");
		standardMultiplication.close();
		hadamardMultiplication.close();
		addition.close();
		summation.close();
		subtraction.close();
		scaler.close();
		transposition.close();
		updation.close();
		System.out.println("Closed MathOperationsImpl " + toString());
	}
}
