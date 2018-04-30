package com.sellman.andrew.vann.core.math.advice;

class AdviceKeyC extends AdviceKey {
	private static final int HASH_PRIME = 31;
	private final int matrixRowCount;
	private final int matrixColumnCount;

	public AdviceKeyC(int matrixRowCount, int matrixColumnCount) {
		this.matrixRowCount = matrixRowCount;
		this.matrixColumnCount = matrixColumnCount;
	}

	@Override
	public int hashCode() {
		int result = HASH_PRIME + matrixRowCount;
		result = HASH_PRIME * result + matrixColumnCount;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		AdviceKeyC otherKey = (AdviceKeyC) o;
		return matrixRowCount == otherKey.matrixRowCount && matrixColumnCount == otherKey.matrixColumnCount;
	}

}
