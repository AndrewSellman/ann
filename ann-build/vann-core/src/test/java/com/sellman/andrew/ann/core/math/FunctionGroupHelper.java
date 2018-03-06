package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.math.function.Function;
import com.sellman.andrew.ann.core.math.function.FunctionGroup;

public class FunctionGroupHelper implements FunctionGroup {
	private final Function function;
	private final Function prime;

	public FunctionGroupHelper(Function function, Function prime) {
		this.function = function;
		this.prime = prime;
	}

	@Override
	public Function getFunction() {
		return function;
	}

	@Override
	public Function getPrime() {
		return prime;
	}

}
