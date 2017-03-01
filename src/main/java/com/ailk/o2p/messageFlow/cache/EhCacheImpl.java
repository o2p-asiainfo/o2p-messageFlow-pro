package com.ailk.o2p.messageFlow.cache;

import org.springframework.stereotype.Component;


@Component
public class EhCacheImpl implements ICache {

	public Object get(String key) {
		return EhCacheUtils.get(key);
	}

	public void set(String key, Object value) {
		EhCacheUtils.put(key, value);
	}

	public void remove(String key) {
		EhCacheUtils.remove(key);
	}
}
