package com.ailk.o2p.messageFlow.cache;

public interface ICache {

	public Object get(String key);

	public void set(String key, Object value);

	public void remove(String key);
}
