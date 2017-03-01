package com.ailk.o2p.messageFlow.cache;

import org.springframework.stereotype.Component;

@Component
public class JedisImpl implements ICache {

	public Object get(String key) {
		if (key == null) {
			return null;
		}

		return JedisUtils.getObject(key);
	}

	public void set(String key, Object value) {
		JedisUtils.setObject(key, value, 0);
	}

	public void remove(String key) {
		JedisUtils.del(key);
	}

}
