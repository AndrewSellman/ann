package com.sellman.andrew.vann.core.math.task;

import com.sellman.andrew.vann.core.concurrent.AbstractTask;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.core.math.function.Function;

public abstract class AbstractOperationTask extends AbstractTask {
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

	public final Function getFunction() {
		return f;
	}

	public final void setFunction(Function function) {
		this.f = function;
	}

	public final Vector getVectorTarget() {
		return vectorTarget;
	}

	public final void setVectorTarget(Vector vectorTarget) {
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
