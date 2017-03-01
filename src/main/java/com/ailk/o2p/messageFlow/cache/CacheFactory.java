package com.ailk.o2p.messageFlow.cache;

import com.ailk.o2p.messageFlow.utils.SpringContextHolder;


public final class CacheFactory {

	public static final String CACHE_MODEL_MEMCACHE = "MEMCACHE";
	public static final String CACHE_MODEL_JAVA = "JAVA";
	public static final String CACHE_MODEL_EHCACHE = "EHCACHE";
	public static final String CACHE_MODEL_REDIS = "REDIS";
	private CacheFactory() {

	}

	public static ICache newCacheInstance(String cacheBeanName) {
		ICache cache = null;
		if (CACHE_MODEL_EHCACHE.equalsIgnoreCase(cacheBeanName)) {
			cache = SpringContextHolder.getBean(EhCacheImpl.class);
		}
		return cache;
	}

}
