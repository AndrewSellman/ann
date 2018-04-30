package com.sellman.andrew.vann.core.math.advice;

class AdviceKeyA extends AdviceKey {
	private static final int HASH_PRIME = 31;
	private final int matrixARowCount;
	private final int matrixAColumnCount;
	private final int matrixBRowCount;
	private final int matrixBColumnCount;

	public AdviceKeyA(int matrixARowCount, int matrixAColumnCount, int matrixBRowCount, int matrixBColumnCount) {
		this.matrixARowCount = matrixARowCount;
		this.matrixAColumnCount = matrixAColumnCount;
		this.matrixBRowCount = matrixBRowCount;
		this.matrixBColumnCount = matrixBColumnCount;
	}

	@Override
	public int hashCode() {
		int result = HASH_PRIME + matrixARowCount;
		result = HASH_PRIME * result + matrixAColumnCount;
		result = HASH_PRIME * result + matrixBRowCount;
		result = HASH_PRIME * result + matrixBColumnCount;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		AdviceKeyA otherKey = (AdviceKeyA) o;
		return matrixARowCount == otherKey.matrixARowCount 
				&& matrixAColumnCount == otherKey.matrixAColumnCount 
				&& matrixBRowCount == otherKey.matrixBRowCount
				&& matrixBColumnCount == otherKey.matrixBColumnCount;
	}
}
