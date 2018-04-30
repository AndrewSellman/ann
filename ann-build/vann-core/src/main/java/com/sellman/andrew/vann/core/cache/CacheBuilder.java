package com.sellman.andrew.vann.core.cache;

import java.util.Properties;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.engine.CompositeCacheAttributes;
import org.apache.commons.jcs.engine.behavior.ICompositeCacheAttributes;

public class CacheBuilder<K, V> {
	private final String cacheName;
	private CacheType cacheType = CacheType.LRU;
	private int maxSize = 1000;
	private int maxIdleLifeSeconds = 14400;
	private boolean useMemoryShrinker = true;
	private int memoryShrinkerIntervalSeconds = 1800;
	
	public CacheBuilder(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public final CacheBuilder<K, V> cacheType(CacheType cacheType) {
		this.cacheType = cacheType;
		return this;
	}
	
	public final CacheBuilder<K, V> useMemoryShrinker(boolean useMemoryShrinker) {
		this.useMemoryShrinker = useMemoryShrinker;
		return this;
	}
	
	public final CacheBuilder<K, V> memoryShrinkerIntervalSeconds(int memoryShrinkerIntervalSeconds) {
		this.memoryShrinkerIntervalSeconds = memoryShrinkerIntervalSeconds;
		return this;
	}
	
	public final CacheBuilder<K, V> maxSize(int maxSize) {
		this.maxSize = maxSize;
		return this;
	}
	
	public final CacheBuilder<K, V> maxIdleLifeSeconds(int maxIdleLifeSeconds) {
		this.maxIdleLifeSeconds = maxIdleLifeSeconds;
		return this;
	}
	
	public final Cache<K, V> build() {
		JCS.setConfigProperties(new Properties());
		ICompositeCacheAttributes cacheConfig = getCacheConfiguration();
		CacheAccess<K, V> cacheAccess = JCS.getInstance(cacheName, cacheConfig);
		return new Cache<K, V>(cacheAccess);
	}

	private ICompositeCacheAttributes getCacheConfiguration() {
		ICompositeCacheAttributes cacheConfig = new CompositeCacheAttributes();
		cacheConfig.setCacheName(cacheName);
		cacheConfig.setMaxMemoryIdleTimeSeconds(maxIdleLifeSeconds);
		cacheConfig.setMaxObjects(maxSize);
		cacheConfig.setUseMemoryShrinker(useMemoryShrinker);
		cacheConfig.setShrinkerIntervalSeconds(memoryShrinkerIntervalSeconds);
		cacheConfig.setUseDisk(false);
		cacheConfig.setUseLateral(false);
		cacheConfig.setUseRemote(false);
		cacheConfig.setMemoryCacheName(cacheType.toString());
		return cacheConfig;
	}
	
}
