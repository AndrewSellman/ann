package com.sellman.andrew.ann.core.math.function;

public enum FunctionType implements FunctionGroup {

	LOGISTIC(new LogisticFunction(), new LogisticPrimeFunction());

	private FunctionType(Function f, Function prime) {
		this.f = f;
		this.prime = prime;
	}

	private Function f;
	private Function prime;

	@Override
	public Function getFunction() {
		return f;
	}

	@Override
	public Function getPrime() {
		return prime;
	}

}
