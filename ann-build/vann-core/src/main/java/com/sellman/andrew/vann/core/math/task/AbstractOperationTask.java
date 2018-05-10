package com.sellman.andrew.vann.core.math.task;

import com.sellman.andrew.vann.core.concurrent.AbstractTask;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.InspectableMatrix;
import com.sellman.andrew.vann.core.math.function.Function;

public abstract class AbstractOperationTask extends AbstractTask {
	private InspectableMatrix a;
	private InspectableMatrix b;
	private Function f;
	private Matrix matrixTarget;
	private ColumnVector vectorTarget;

	public final InspectableMatrix getMatrixA() {
		return a;
	}

	public final void setMatrixA(final InspectableMatrix a) {
		this.a = a;
	}

	public final InspectableMatrix getMatrixLeft() {
		return getMatrixA();
	}

	public final void setMatrixLeft(InspectableMatrix left) {
		setMatrixA(left);
	}

	public final InspectableMatrix getMatrixSource() {
		return getMatrixA();
	}

	public final void setMatrixSource(InspectableMatrix m) {
		setMatrixA(m);
	}

	public final InspectableMatrix getMatrixB() {
		return b;
	}

	public final void setMatrixB(final InspectableMatrix b) {
		this.b = b;
	}

	public final InspectableMatrix getMatrixRight() {
		return getMatrixB();
	}

	public final void setMatrixRight(InspectableMatrix right) {
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

	public final ColumnVector getVectorTarget() {
		return vectorTarget;
	}

	public final void setVectorTarget(ColumnVector vectorTarget) {
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
