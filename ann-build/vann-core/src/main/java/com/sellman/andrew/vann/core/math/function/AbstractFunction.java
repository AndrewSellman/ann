package com.sellman.andrew.vann.core.math.function;

abstract class AbstractFunction implements Function {
	private final String distinguishedName;
	private final int hashCode;

	public AbstractFunction(String distinguishedName) {
		this.distinguishedName = distinguishedName;
		hashCode = distinguishedName.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		AbstractFunction other = (AbstractFunction) o;
		return other.distinguishedName.equals(this.distinguishedName);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

}
