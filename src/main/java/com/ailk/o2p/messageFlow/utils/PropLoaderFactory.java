package com.ailk.o2p.messageFlow.utils;

import com.ailk.o2p.messageFlow.cache.CacheFactory;
import com.ailk.o2p.messageFlow.cache.ICache;

public final class PropLoaderFactory {

	private PropLoaderFactory() {

	}

	public static PropertiesLoader getPropertiesLoader(String propName) {
		ICache cache = CacheFactory.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
		PropertiesLoader loader = (PropertiesLoader) cache.get(propName);
		cache.remove(propName);
		return loader;
	}

}
