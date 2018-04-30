package com.sellman.andrew.vann.core.cache;

import org.apache.commons.jcs.access.CacheAccess;

public class Cache<K, V> implements AutoCloseable {
	private final CacheAccess<K, V> cacheImpl;

	protected Cache(CacheAccess<K, V> delegate) {
		this.cacheImpl = delegate;
	}

	public void store(K cacheKey, V cacheValue) {
		cacheImpl.put(cacheKey, cacheValue);
	}

	public V retrieve(K cacheKey) {
		return cacheImpl.get(cacheKey);
	}

	public void clear() {
		cacheImpl.clear();
	}

	@Override
	public void close() throws Exception {
		cacheImpl.dispose();
	}

}
