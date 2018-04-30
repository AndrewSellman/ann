package com.sellman.andrew.vann.core.cache;

public enum CacheType {

	LRU("org.apache.commons.jcs.engine.memory.lru.LRUMemoryCache");
	
	private String className;
	
	private CacheType(String className) {
		this.className = className;
	}
	
	public String toString() {
		return className;
	}
	
}
