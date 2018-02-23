package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

abstract class AbstractMatrixTask extends AbstractTask {
	private Matrix a;
	private Matrix b;
	private Matrix target;

	public final Matrix getMatrixA() {
		return a;
	}

	public final void setMatrixA(Matrix a) {
		this.a = a;
	}

	public final Matrix getMatrixB() {
		return b;
	}

	public final void setMatrixB(Matrix b) {
		this.b = b;
	}

	public final Matrix getMatrixTarget() {
		return target;
	}

	public final void setMatrixTarget(Matrix target) {
		this.target = target;
	}

	public void recycle() {
		super.recycle();
		a = null;
		b = null;
		target = null;
	}

}
