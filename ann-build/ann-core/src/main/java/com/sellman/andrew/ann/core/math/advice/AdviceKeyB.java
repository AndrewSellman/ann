package com.sellman.andrew.ann.core.math.advice;

import com.sellman.andrew.ann.core.math.function.Function;

class AdviceKeyB extends AdviceKey {
	private static final int HASH_PRIME = 31;
	private final int matrixRowCount;
	private final int matrixColumnCount;
	private final int functionHashCode;

	public AdviceKeyB(int matrixRowCount, int matrixColumnCount, Function function) {
		this.matrixRowCount = matrixRowCount;
		this.matrixColumnCount = matrixColumnCount;
		functionHashCode = function.hashCode();
	}

	@Override
	public int hashCode() {
		int result = HASH_PRIME + matrixRowCount;
		result = HASH_PRIME * result + matrixColumnCount;
		result = HASH_PRIME * result + functionHashCode;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		AdviceKeyB otherKey = (AdviceKeyB) o;
		return matrixRowCount == otherKey.matrixRowCount && matrixColumnCount == otherKey.matrixColumnCount && functionHashCode == otherKey.functionHashCode;
	}

}
