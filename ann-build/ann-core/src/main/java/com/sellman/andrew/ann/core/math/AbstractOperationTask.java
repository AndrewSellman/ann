package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

abstract class AbstractOperationTask extends AbstractTask {
	private Matrix a;
	private Matrix b;
	private Function f;
	private Matrix matrixTarget;
	private Vector vectorTarget;

	public final Matrix getMatrixA() {
		return a;
	}

	public final void setMatrixA(final Matrix a) {
		this.a = a;
	}

	public final Matrix getMatrixLeft() {
		return getMatrixA();
	}

	public final void setMatrixLeft(Matrix left) {
		setMatrixA(left);
	}

	public final Matrix getMatrixSource() {
		return getMatrixA();
	}

	public final void setMatrixSource(Matrix m) {
		setMatrixA(m);
	}

	public final Matrix getMatrixB() {
		return b;
	}

	public final void setMatrixB(final Matrix b) {
		this.b = b;
	}

	public final Matrix getMatrixRight() {
		return getMatrixB();
	}

	public final void setMatrixRight(Matrix right) {
		setMatrixB(right);
	}

	public final Matrix getMatrixTarget() {
		return matrixTarget;
	}

	public final void setMatrixTarget(final Matrix target) {
		this.matrixTarget = target;
	}

	public Function getFunction() {
		return f;
	}

	public void setFunction(Function function) {
		this.f = function;
	}

	public Vector getVectorTarget() {
		return vectorTarget;
	}

	public void setVectorTarget(Vector vectorTarget) {
		this.vectorTarget = vectorTarget;
	}

	@Override
	public void recycle() {
		super.recycle();
		a = null;
		b = null;
		f = null;
		matrixTarget = null;
		vectorTarget = null;
	}

}
